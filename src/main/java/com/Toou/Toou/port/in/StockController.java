package com.Toou.Toou.port.in;

import com.Toou.Toou.domain.model.MarketType;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
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

	@Value("${stock.history-start-date}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate STOCK_HISTORY_START_DATE;

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
		LocalDate todayDate = getTodayDate();
		dateFrom = dateFrom == null ? STOCK_HISTORY_START_DATE : dateFrom;
		dateTo = dateTo == null ? todayDate : dateTo;

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
				todayDate, output.getStockCode(), output.getStockName(), output.getMarketType());
		return ResponseEntity.ok().body(response);
	}

	private static LocalDate convertStringToLocalDate(String dateStr) {
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	private static boolean isValidDateRange(LocalDate dateFrom, LocalDate dateTo) {
		return dateFrom.isEqual(dateTo) || dateFrom.isBefore(dateTo);
	}

	private static StockHistoryListResponse buildStockHistoryListResponse(
			List<StockDailyHistory> stockDailyHistories, LocalDate newestDate, String stockCode,
			String stockName,
			MarketType marketType) {
		StockDailyHistory firstHistory = stockDailyHistories.stream().findFirst()
				.orElseThrow(() -> new CustomException(CustomExceptionDetail.STOCK_NOT_FOUND));
		StockDailyHistory lastHistory = stockDailyHistories.get(stockDailyHistories.size() - 1);

		return new StockHistoryListResponse(
				true,
				firstHistory.getId(),
				stockCode,
				// stockMetadataId가 아닌 stockCode 사용을 고려하여 수정 필요
				stockName,  // stockName도 마찬가지
				marketType.toString(),  // 시장 정보를 가져오는 로직이 필요
				lastHistory.getClosingPrice(),
				newestDate,
				stockDailyHistories.stream()
						.map(StockDailyHistoryDto::fromDomainModel)
						.toList()
		);
	}

	private LocalDate getTodayDate() {
		return LocalDate.now();
	}
}
