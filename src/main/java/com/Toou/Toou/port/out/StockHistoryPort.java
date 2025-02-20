package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.StockDailyHistory;

import java.time.LocalDate;
import java.util.List;

public interface StockHistoryPort {

	List<StockDailyHistory> findAllHistoriesBetweenDates(Long stockMetadataId, LocalDate dateFrom,
			LocalDate dateTo);

	StockDailyHistory findStockHistoryByDate(Long stockMetadataId, LocalDate date);

	StockDailyHistory save(StockDailyHistory stockDailyHistory);
}
