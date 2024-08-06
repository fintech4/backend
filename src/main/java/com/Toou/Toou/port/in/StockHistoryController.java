package com.Toou.Toou.port.in;

import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.in.dto.StockDailyHistoryDto;
import com.Toou.Toou.port.in.dto.StockHistoryListResponse;
import com.Toou.Toou.usecase.ListStockHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock/history")
public class StockHistoryController {

    private final ListStockHistoryUseCase listStockHistoryUseCase;

    @GetMapping("/company/{companyCode}")
    ResponseEntity<StockHistoryListResponse> listStockHistories(
        @PathVariable("companyCode") String companyCode,
        @RequestParam("dateFrom") String dateFromStr, // "yyyy-MM-dd"
        @RequestParam("dateTo") String dateToStr // "yyyy-MM-dd"
    ) {
        LocalDate dateFrom = convertStringToLocalDate(dateFromStr);
        LocalDate dateTo = convertStringToLocalDate(dateToStr);

        if (!isValidDateRange(dateFrom, dateTo)) {
            return ResponseEntity.badRequest().build();
        }

        ListStockHistoryUseCase.Input input = new ListStockHistoryUseCase.Input(
            companyCode,
            dateFrom,
            dateTo
        );
        ListStockHistoryUseCase.Output output = listStockHistoryUseCase.execute(input);
        StockDailyHistory firstHistory = output.getDailyHistories().stream().findFirst().orElseThrow();
        StockHistoryListResponse response = new StockHistoryListResponse(
            firstHistory.getId(),
            firstHistory.getCompanyCode(),
            output.getDailyHistories().stream().map(StockDailyHistoryDto::fromDomainModel).toList()
        );
        return ResponseEntity.ok().body(response);
    }

    private static LocalDate convertStringToLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private static boolean isValidDateRange(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom.isEqual(dateTo) || dateFrom.isBefore(dateTo);
    }
}
