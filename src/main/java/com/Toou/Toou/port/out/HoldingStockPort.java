package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.HoldingIndividualStock;
import java.util.List;

public interface HoldingStockPort {

	List<HoldingIndividualStock> findAllHoldingsByAccountAssetId(Long accountAssetId);

	HoldingIndividualStock findHoldingByStockCodeAndAssetId(String StockCode, Long assetId);

	HoldingIndividualStock save(HoldingIndividualStock holdingIndividualStock);

	void delete(HoldingIndividualStock holdingIndividualStock);
}
