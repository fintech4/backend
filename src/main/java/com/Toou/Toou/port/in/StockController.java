package com.Toou.Toou.port.in;

import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.in.dto.StockDailyHistoryDto;
import com.Toou.Toou.port.in.dto.StockHistoryListResponse;
import com.Toou.Toou.port.in.dto.StockMetadataDto;
import com.Toou.Toou.port.in.dto.StockSearchListResponse;
import com.Toou.Toou.usecase.ListStockHistoryUseCase;
import com.Toou.Toou.usecase.ListStockMetadataUseCase;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stocks")
public class StockController {

	private final ListStockMetadataUseCase listStockMetadataUseCase;
	private final ListStockHistoryUseCase listStockHistoryUseCase;

	private final LocalDate DUMMY_START_DATE = LocalDate.of(2023, 1, 1);
	private final LocalDate DUMMY_NEWEST_DATE = LocalDate.of(2023, 12, 31);

	@GetMapping
	ResponseEntity<StockSearchListResponse> listStockMetadataByName(@RequestParam final String name,
			@RequestParam(required = false) final Integer limit) {

		//TODO: name 이상하게 오면 에러 응답
		ListStockMetadataUseCase.Input input =
				limit == null
						? new ListStockMetadataUseCase.Input(name, 4)
						: new ListStockMetadataUseCase.Input(name, limit);
		ListStockMetadataUseCase.Output output = listStockMetadataUseCase.execute(input);
		StockSearchListResponse response = new StockSearchListResponse(
				true,
				output.getStokMetadataList().stream().map(StockMetadataDto::fromDomainModel).toList()
		);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{stockCode}/history")
	ResponseEntity<StockHistoryListResponse> listStockHistories(
			@PathVariable("stockCode") String stockCode,
			@RequestParam(value = "dateFrom", required = false) LocalDate dateFrom, // "yyyy-MM-dd"
			@RequestParam(value = "dateTo", required = false) LocalDate dateTo // "yyyy-MM-dd"
	) {
		dateFrom = dateFrom == null ? DUMMY_START_DATE : dateFrom;
		dateTo = dateTo == null ? DUMMY_NEWEST_DATE : dateTo;

		if (!isValidDateRange(dateFrom, dateTo)) {
			return ResponseEntity.badRequest().build();
		}

		ListStockHistoryUseCase.Input input = new ListStockHistoryUseCase.Input(
				stockCode,
				dateFrom,
				dateTo
		);
		ListStockHistoryUseCase.Output output = listStockHistoryUseCase.execute(input);
		List<StockDailyHistory> stockDailyHistories = output.getDailyHistories();
		StockHistoryListResponse response = buildStockHistoryListResponse(stockDailyHistories,
				DUMMY_NEWEST_DATE);
		return ResponseEntity.ok().body(response);
	}

	private static LocalDate convertStringToLocalDate(String dateStr) {
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	private static boolean isValidDateRange(LocalDate dateFrom, LocalDate dateTo) {
		return dateFrom.isEqual(dateTo) || dateFrom.isBefore(dateTo);
	}
	
	private static StockHistoryListResponse buildStockHistoryListResponse(
			List<StockDailyHistory> stockDailyHistories, LocalDate newestDate) {
		StockDailyHistory firstHistory = stockDailyHistories.stream().findFirst()
				.orElseThrow(() -> new IllegalStateException("No stock history found"));
		StockDailyHistory lastHistory = stockDailyHistories.get(stockDailyHistories.size() - 1);

		return new StockHistoryListResponse(
				true,
				firstHistory.getId(),
				firstHistory.getStockCode(),
				firstHistory.getStockName(),
				lastHistory.getPrices().get(3),
				newestDate,
				stockDailyHistories.stream().map(StockDailyHistoryDto::fromDomainModel).toList()
		);
	}
}
