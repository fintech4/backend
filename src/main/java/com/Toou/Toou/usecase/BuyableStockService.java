package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.StockBuyable;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.port.out.AccountAssetPort;
import com.Toou.Toou.port.out.StockHistoryPort;
import com.Toou.Toou.port.out.StockMetadataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyableStockService implements BuyableStockUseCase {

	private final AccountAssetPort accountAssetPort;
	private final StockMetadataPort stockMetadataPort;
	private final StockHistoryPort stockHistoryPort;

	@Override
	public Output execute(Input input) {
		AccountAsset accountAsset = accountAssetPort.findAssetByKakaoId(input.kakaoId);
		StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(input.stockCode);
		StockDailyHistory stockDailyHistory = stockHistoryPort.findStockHistoryByDate(
				stockMetadata.getId(), input.buyDate);
		Long closingPrice = stockDailyHistory.getClosingPrice();
		Long deposit = accountAsset.getDeposit();
		Long buyableQuantity = deposit / closingPrice;
		StockBuyable stockBuyable = new StockBuyable(input.stockCode, stockMetadata.getStockName(),
				closingPrice, deposit,
				buyableQuantity);
		return new Output(stockBuyable);
	}
}
