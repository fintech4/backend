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
import java.util.Optional;
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
		if (input.stockOrder.getTradeType() == TradeType.BUY) {
			handleBuyOrder(input.stockOrder, accountAsset, input.orderDate);
		} else if (input.stockOrder.getTradeType() == TradeType.SELL) {
			handleSellOrder(input.stockOrder, accountAsset, input.orderDate);
		}

		return new Output();
	}

	private void handleBuyOrder(StockOrder stockOrder, AccountAsset accountAsset,
			LocalDate orderDate) {
		Optional<HoldingIndividualStock> holdingIndividualStockOptional = holdingStockPort.findHoldingByStockCodeAndAssetId(
				stockOrder.getStockCode(), accountAsset.getId());

		StockBuyable stockBuyable = getStockBuyable(accountAsset.getKakaoId(),
				stockOrder.getStockCode(), orderDate);

		if (stockBuyable.getBuyableQuantity() == 0) {
			new CustomException(CustomExceptionDetail.WRONG_BUY_ORDER);
		}

		// 주문 금액 계산 및 deposit 감소
		Long totalCost = calculateTotalCost(stockOrder);

		//총 자산 변경
		accountAsset.setDeposit(accountAsset.getDeposit() - totalCost);
		AccountAsset savedAccountAsset = accountAssetPort.saveAsset(accountAsset);

		// 보유 주식이 없다면 새로 추가
		if (holdingIndividualStockOptional.isEmpty()) {
			HoldingIndividualStock newHoldingIndividualStock = new HoldingIndividualStock(stockOrder,
					savedAccountAsset.getId());
			HoldingIndividualStock savedHoldingIndividualStock = holdingStockPort.save(
					newHoldingIndividualStock);
		} else {
			// 이미 보유한 주식이라면 수량과 평균 매입가, 평가 금액 업데이트
			HoldingIndividualStock holdingIndividualStock = holdingIndividualStockOptional.get();
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
		Optional<HoldingIndividualStock> holdingIndividualStockOptional = holdingStockPort.findHoldingByStockCodeAndAssetId(
				stockOrder.getStockCode(), accountAsset.getId());

		StockSellable stockSellable = getStockSellable(accountAsset.getKakaoId(),
				stockOrder.getStockCode(), orderDate);

		if (stockSellable.getSellableQuantity() == 0) {
			new CustomException(CustomExceptionDetail.NO_HOLDING_STOCK);
		}
		if (stockOrder.getOrderQuantity() > stockSellable.getSellableQuantity()) {
			new CustomException(CustomExceptionDetail.WRONG_SELL_QUANTITY);
		}

		// 판매 금액 계산 및 deposit 증가
		Long totalSale = calculateTotalCost(stockOrder);
		accountAsset.setDeposit(accountAsset.getDeposit() + totalSale);

		// 주식 수량 및 평가 금액 업데이트
		HoldingIndividualStock holdingIndividualStock = holdingIndividualStockOptional.get();
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
		Long closingPrice = stockDailyHistory.getPrices().get(3);
		Long deposit = accountAsset.getDeposit();
		Long buyableQuantity = deposit / closingPrice;
		return new StockBuyable(stockCode, stockMetadata.getStockName(),
				closingPrice, deposit,
				buyableQuantity);
	}

	private StockSellable getStockSellable(String kakaoId, String stockCode, LocalDate buyDate) {
		AccountAsset accountAsset = accountAssetPort.findAssetByKakaoId(kakaoId);
		Optional<HoldingIndividualStock> holdingIndividualStock = holdingStockPort.findHoldingByStockCodeAndAssetId(
				stockCode, accountAsset.getId());
		StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(stockCode);
		Long sellableQuantity =
				holdingIndividualStock.isPresent() ? holdingIndividualStock.get().getQuantity() : 0L;
		return new StockSellable(stockCode, stockMetadata.getStockName(), sellableQuantity);
	}
}
