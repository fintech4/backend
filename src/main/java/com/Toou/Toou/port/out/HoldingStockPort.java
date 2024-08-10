package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import java.util.List;

public interface HoldingStockPort {

	List<HoldingIndividualStock> findAllHoldings(AccountAsset accountAsset);
}
