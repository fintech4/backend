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

		boolean isFirstBuy = holdingIndividualStock == null;
		HoldingIndividualStock updatedHoldingIndividualStock = isFirstBuy
				? new HoldingIndividualStock(stockOrder, accountAsset.getId())
				: holdingIndividualStock.updateWhenBuyStock(stockOrder);

		AccountAsset updatedAccountAsset = accountAsset.updateWhenBuyStock(stockOrder, isFirstBuy);
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

		boolean isLastStockSold = holdingIndividualStock.getQuantity() == stockOrder.getOrderQuantity();
		HoldingIndividualStock updatedHoldingIndividualStock = isLastStockSold
				? null
				: holdingIndividualStock.updateWhenSellStock(stockOrder);

		AccountAsset updatedAccountAsset = accountAsset.updateWhenSellStock(stockOrder,
				isLastStockSold);

		if (isLastStockSold) {
			holdingStockPort.delete(holdingIndividualStock);
		} else {
			holdingStockPort.save(updatedHoldingIndividualStock);
		}
		AccountAsset savedAccountAsset = accountAssetPort.saveAsset(updatedAccountAsset);
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
