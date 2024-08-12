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
import java.util.Objects;
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
		StockDailyHistory stockDailyHistory = stockHistoryPort.findStockHistoryByDate(
				stockMetadata.getId(), input.orderDate);
		if (stockDailyHistory == null) {
			throw new CustomException(CustomExceptionDetail.STOCK_HISTORY_NOT_FOUND);
		}

		StockOrder stockOrder = new StockOrder(input.stockCode, stockMetadata.getStockName(),
				stockDailyHistory.getClosingPrice(), input.orderQuantity, input.tradeType, accountAsset);
		if (input.tradeType == TradeType.BUY) {
			validateBuy(accountAsset, stockOrder, input.orderDate);
			handleBuyOrder(stockOrder, accountAsset);
		} else if (input.tradeType == TradeType.SELL) {
			validateSell(accountAsset, stockOrder, input.orderDate);
			handleSellOrder(stockOrder, accountAsset);
		}

		return new Output();
	}

	private void handleBuyOrder(StockOrder stockOrder, AccountAsset accountAsset) {
		HoldingIndividualStock holdingIndividualStock = holdingStockPort.findHoldingByStockCodeAndAssetId(
				stockOrder.getStockCode(), accountAsset.getId());
		HoldingIndividualStock updatedHoldingIndividualStock = updateHoldingIndividualStock(stockOrder,
				holdingIndividualStock, accountAsset);

		Long totalPrice = calculateTotalPrice(stockOrder);
		boolean isStockEmpty = holdingIndividualStock == null;
		AccountAsset updatedAccountAsset = updateAsset(totalPrice, accountAsset,
				stockOrder.getTradeType(), isStockEmpty);

		HoldingIndividualStock savedHoldingIndividualStock = holdingStockPort.save(
				updatedHoldingIndividualStock);
		accountAssetPort.saveAsset(updatedAccountAsset);

	}

	private void handleSellOrder(StockOrder stockOrder, AccountAsset accountAsset) {
		// 현재 보유한 주식을 가져옴
		HoldingIndividualStock holdingIndividualStock = holdingStockPort.findHoldingByStockCodeAndAssetId(
				stockOrder.getStockCode(), accountAsset.getId());

		if (holdingIndividualStock == null) {
			throw new CustomException(CustomExceptionDetail.NO_HOLDING_STOCK);
		}

		HoldingIndividualStock updatedHoldingIndividualStock = updateHoldingIndividualStock(stockOrder,
				holdingIndividualStock, accountAsset);

		Long totalPrice = calculateTotalPrice(stockOrder);
		boolean isStockEmpty = false;
		AccountAsset updatedAccountAsset = updateAsset(totalPrice, accountAsset,
				stockOrder.getTradeType(), isStockEmpty);

		if (updatedHoldingIndividualStock != null) {
			holdingStockPort.save(updatedHoldingIndividualStock);
		}
		AccountAsset savedAccountAsset = accountAssetPort.saveAsset(updatedAccountAsset);
	}

	private HoldingIndividualStock updateHoldingIndividualStock(StockOrder stockOrder,
			HoldingIndividualStock holdingIndividualStock, AccountAsset accountAsset) {

		if (stockOrder.getTradeType() == TradeType.BUY && holdingIndividualStock == null) {
			return new HoldingIndividualStock(stockOrder, accountAsset.getId());
		}

		if (stockOrder.getTradeType() == TradeType.SELL && Objects.equals(
				holdingIndividualStock.getQuantity(), stockOrder.getOrderQuantity())) {
			holdingStockPort.delete(holdingIndividualStock);
			return null;
		}

		//보유 주식 수, 평균 매수가 변경
		Long totalPrice = calculateTotalPrice(stockOrder);
		Long newQuantity =
				stockOrder.getTradeType() == TradeType.BUY
						? holdingIndividualStock.getQuantity() + stockOrder.getOrderQuantity()
						: holdingIndividualStock.getQuantity() - stockOrder.getOrderQuantity();
		Long newValuation = stockOrder.getTradeType() == TradeType.BUY
				? holdingIndividualStock.getValuation() + totalPrice
				: holdingIndividualStock.getValuation() - totalPrice;
		Long newAveragePurchasePrice = newValuation / newQuantity;
		Double newYield =
				((double) (stockOrder.getStockPrice() - newAveragePurchasePrice) / newAveragePurchasePrice)
						* 100;

		return new HoldingIndividualStock(
				holdingIndividualStock.getId(),
				holdingIndividualStock.getStockCode(),
				holdingIndividualStock.getStockName(),
				holdingIndividualStock.getCurrentPrice(),
				newAveragePurchasePrice,
				newQuantity,
				newValuation,
				newYield,
				holdingIndividualStock.getAccountAssetId()
		);
	}

	private AccountAsset updateAsset(Long totalPrice, AccountAsset accountAsset,
			TradeType tradeType, boolean isStockEmpty) {
		// 총 자산, 투자 수익률 변화 x
		// 예수금 변화
		// 총 투자금 변화
		// 총 종목수 변화
		if (tradeType == TradeType.BUY) {
			accountAsset.setDeposit(accountAsset.getDeposit() - totalPrice);
			accountAsset.setTotalHoldingsValue(accountAsset.getTotalHoldingsValue() + totalPrice);

			if (isStockEmpty) {
				accountAsset.setTotalHoldingsQuantity(accountAsset.getTotalHoldingsQuantity() + 1);
			}
			return accountAsset;
		}
		accountAsset.setDeposit(accountAsset.getDeposit() + totalPrice);
		accountAsset.setTotalHoldingsValue(accountAsset.getTotalHoldingsValue() - totalPrice);

		if (isStockEmpty) {
			accountAsset.setTotalHoldingsQuantity(accountAsset.getTotalHoldingsQuantity() + 1);
		}
		return accountAsset;
	}

	private Long calculateTotalPrice(StockOrder stockOrder) {
		return stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
	}

	private void validateBuy(AccountAsset accountAsset, StockOrder stockOrder, LocalDate orderDate) {
		StockBuyable stockBuyable = getStockBuyable(accountAsset.getKakaoId(),
				stockOrder.getStockCode(), orderDate);

		if (stockBuyable.getBuyableQuantity() < stockOrder.getOrderQuantity()) {
			throw new CustomException(CustomExceptionDetail.WRONG_BUY_ORDER);
		}

	}

	private void validateSell(AccountAsset accountAsset, StockOrder stockOrder, LocalDate orderDate) {
		StockSellable stockSellable = getStockSellable(accountAsset.getKakaoId(),
				stockOrder.getStockCode(), orderDate);

		if (stockOrder.getOrderQuantity() > stockSellable.getSellableQuantity()) {
			throw new CustomException(CustomExceptionDetail.WRONG_SELL_QUANTITY);
		}

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
