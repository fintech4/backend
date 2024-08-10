package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.HoldingIndividualStock;
import java.util.List;
import java.util.Optional;

public interface HoldingStockPort {

	List<HoldingIndividualStock> findAllHoldingsByAccountAssetId(Long accountAssetId);

	Optional<HoldingIndividualStock> findHoldingByStockCodeAndAssetId(String StockCode, Long assetId);

	HoldingIndividualStock save(HoldingIndividualStock holdingIndividualStock);

	void delete(HoldingIndividualStock holdingIndividualStock);
}
