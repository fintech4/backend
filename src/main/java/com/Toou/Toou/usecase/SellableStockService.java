package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.domain.model.StockSellable;
import com.Toou.Toou.port.out.AccountAssetPort;
import com.Toou.Toou.port.out.HoldingStockPort;
import com.Toou.Toou.port.out.StockMetadataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellableStockService implements SellableStockUseCase {

	private final AccountAssetPort accountAssetPort;
	private final StockMetadataPort stockMetadataPort;
	private final HoldingStockPort holdingStockPort;

	@Override
	public Output execute(Input input) {
		AccountAsset accountAsset = accountAssetPort.findByProviderId(input.providerId);
		HoldingIndividualStock holdingIndividualStock = holdingStockPort.findHoldingByStockCodeAndAssetId(
				input.stockCode, accountAsset.getId());
		StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(input.stockCode);
		StockSellable stockSellable = StockSellable.of(holdingIndividualStock,
				stockMetadata.getStockCode(), stockMetadata.getStockName());
		return new Output(stockSellable);
	}
}
