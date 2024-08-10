package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.StockOrder;

public interface StockOrderPort {

	void orderStock(StockOrder stockOrder, AccountAsset accountAsset);
}
