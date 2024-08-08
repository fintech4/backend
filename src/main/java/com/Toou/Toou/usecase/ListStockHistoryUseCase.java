package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockDailyHistory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public interface ListStockHistoryUseCase {

	Output execute(Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String stockCode;
		LocalDate dateFrom;
		LocalDate dateTo;
	}

	@AllArgsConstructor
	@Data
	class Output {

		List<StockDailyHistory> dailyHistories;
	}
}
