package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.StockDailyHistory;

import java.time.LocalDate;
import java.util.List;

public interface StockOpenApiPort {

	List<StockDailyHistory> findAllHistoriesBetweenDates(Long stockMetadataId, String companyName,
			LocalDate dateFrom,
			LocalDate dateTo);
}
