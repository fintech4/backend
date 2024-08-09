package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.StockBuyable;
import java.time.LocalDate;

public interface BuyableStockPort {

	StockBuyable getStockBuyableByStockCode(String stockCode, LocalDate buyDate,
			AccountAsset accountAsset);
}
