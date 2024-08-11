package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.domain.model.StockBuyable;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.domain.model.StockOrder;
import com.Toou.Toou.domain.model.StockSellable;
import com.Toou.Toou.domain.model.TradeType;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
import com.Toou.Toou.port.out.AccountAssetPort;
import com.Toou.Toou.port.out.HoldingStockPort;
import com.Toou.Toou.port.out.StockHistoryPort;
import com.Toou.Toou.port.out.StockMetadataPort;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockOrderService implements StockOrderUseCase {

	private final AccountAssetPort accountAssetPort;
	private final HoldingStockPort holdingStockPort;
	private final StockMetadataPort stockMetadataPort;
	private final StockHistoryPort stockHistoryPort;


	@Override
	public Output execute(Input input) {
		AccountAsset accountAsset = accountAssetPort.findAssetByKakaoId(input.kakaoId);
		StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(input.stockCode);
		if (stockMetadata == null) {
			throw new CustomException(CustomExceptionDetail.STOCK_NOT_FOUND);
		}
		System.out.println("종목 코드 : " + stockMetadata.getStockCode());
		System.out.println("종목 명 : " + stockMetadata.getStockName());

		StockDailyHistory stockDailyHistory = stockHistoryPort.findStockHistoryByDate(
				stockMetadata.getId(), input.orderDate);
		if (stockDailyHistory == null) {
			throw new CustomException(CustomExceptionDetail.STOCK_HISTORY_NOT_FOUND);
		}
		StockOrder stockOrder = new StockOrder(input.stockCode, stockMetadata.getStockName(),
				stockDailyHistory.getClosingPrice(), input.orderQuantity, input.tradeType, accountAsset);

		if (input.tradeType == TradeType.BUY) {
			handleBuyOrder(stockOrder, accountAsset, input.orderDate);
		} else if (input.tradeType == TradeType.SELL) {
			handleSellOrder(stockOrder, accountAsset, input.orderDate);
		}

		return new Output();
	}

	private void handleBuyOrder(StockOrder stockOrder, AccountAsset accountAsset,
			LocalDate orderDate) {
		HoldingIndividualStock holdingIndividualStock = holdingStockPort.findHoldingByStockCodeAndAssetId(
				stockOrder.getStockCode(), accountAsset.getId());

		StockBuyable stockBuyable = getStockBuyable(accountAsset.getKakaoId(),
				stockOrder.getStockCode(), orderDate);

		if (stockBuyable.getBuyableQuantity() < stockOrder.getOrderQuantity()) {
			throw new CustomException(CustomExceptionDetail.WRONG_BUY_ORDER);
		}

		// 주문 금액 계산 및 deposit 감소
		Long totalCost = calculateTotalCost(stockOrder);

		//총 자산 변경
		accountAsset.setDeposit(accountAsset.getDeposit() - totalCost);
		accountAsset.setTotalHoldingsQuantity(
				accountAsset.getTotalHoldingsQuantity() + stockOrder.getOrderQuantity());
		AccountAsset savedAccountAsset = accountAssetPort.saveAsset(accountAsset);

		// 보유 주식이 없다면 새로 추가
		if (holdingIndividualStock == null) {
			HoldingIndividualStock newHoldingIndividualStock = new HoldingIndividualStock(stockOrder,
					savedAccountAsset.getId());
			HoldingIndividualStock savedHoldingIndividualStock = holdingStockPort.save(
					newHoldingIndividualStock);
		} else {
			// 이미 보유한 주식이라면 수량과 평균 매입가, 평가 금액 업데이트
			Long newQuantity = holdingIndividualStock.getQuantity() + stockOrder.getOrderQuantity();
			Long newValuation = holdingIndividualStock.getValuation() + totalCost;
			Long newAveragePurchasePrice = newValuation / newQuantity;

			holdingIndividualStock.setQuantity(newQuantity);
			holdingIndividualStock.setAveragePurchasePrice(newAveragePurchasePrice);
			holdingIndividualStock.setValuation(newValuation);
			holdingIndividualStock.setCurrentPrice(stockOrder.getStockPrice());
			HoldingIndividualStock savedHoldingIndividualStock = holdingStockPort.save(
					holdingIndividualStock);
		}
	}

	private void handleSellOrder(StockOrder stockOrder, AccountAsset accountAsset,
			LocalDate orderDate) {
		// 현재 보유한 주식을 가져옴
		HoldingIndividualStock holdingIndividualStock = holdingStockPort.findHoldingByStockCodeAndAssetId(
				stockOrder.getStockCode(), accountAsset.getId());

		if (holdingIndividualStock == null) {
			throw new CustomException(CustomExceptionDetail.NO_HOLDING_STOCK);
		}

		StockSellable stockSellable = getStockSellable(accountAsset.getKakaoId(),
				stockOrder.getStockCode(), orderDate);

		if (stockOrder.getOrderQuantity() > stockSellable.getSellableQuantity()) {
			throw new CustomException(CustomExceptionDetail.WRONG_SELL_QUANTITY);
		}

		// 판매 금액 계산 및 deposit 증가
		Long totalSale = calculateTotalCost(stockOrder);
		accountAsset.setDeposit(accountAsset.getDeposit() + totalSale);
		accountAsset.setTotalHoldingsQuantity(
				accountAsset.getTotalHoldingsQuantity() - stockOrder.getOrderQuantity());
		AccountAsset savedAccountAsset = accountAssetPort.saveAsset(accountAsset);

		// 주식 수량 및 평가 금액 업데이트
		holdingIndividualStock.setQuantity(
				holdingIndividualStock.getQuantity() - stockOrder.getOrderQuantity());
		holdingIndividualStock.setValuation(holdingIndividualStock.getValuation() - totalSale);
		if (holdingIndividualStock.getQuantity() == 0) {
			holdingStockPort.delete(holdingIndividualStock);
		} else {
			holdingStockPort.save(holdingIndividualStock);
		}
	}

	private Long calculateTotalCost(StockOrder stockOrder) {
		return stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
	}

	private StockBuyable getStockBuyable(String kakaoId, String stockCode, LocalDate buyDate) {
		AccountAsset accountAsset = accountAssetPort.findAssetByKakaoId(kakaoId);
		StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(stockCode);
		StockDailyHistory stockDailyHistory = stockHistoryPort.findStockHistoryByDate(
				stockMetadata.getId(), buyDate);
		Long closingPrice = stockDailyHistory.getClosingPrice();
		Long deposit = accountAsset.getDeposit();
		Long buyableQuantity = deposit / closingPrice;
		return new StockBuyable(stockCode, stockMetadata.getStockName(),
				closingPrice, deposit,
				buyableQuantity);
	}

	private StockSellable getStockSellable(String kakaoId, String stockCode, LocalDate buyDate) {
		AccountAsset accountAsset = accountAssetPort.findAssetByKakaoId(kakaoId);
		HoldingIndividualStock holdingIndividualStock = holdingStockPort.findHoldingByStockCodeAndAssetId(
				stockCode, accountAsset.getId());
		StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(stockCode);
		Long sellableQuantity =
				holdingIndividualStock != null ? holdingIndividualStock.getQuantity() : 0L;
		return new StockSellable(stockCode, stockMetadata.getStockName(), sellableQuantity);
	}
}
