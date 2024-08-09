package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.StockMetadata;
import java.util.List;

public interface StockMetadataPort {

	List<StockMetadata> searchStocksByStockName(String stockName, int limit);
}
