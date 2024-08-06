package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.StockDailyHistory;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class StockDailyHistoryDto {
    private LocalDate date; // 해당 날짜
    private Long price; // 해당 날짜의 종가(KRW)

    public static StockDailyHistoryDto fromDomainModel(StockDailyHistory domainModel) {
        return new StockDailyHistoryDto(
            domainModel.getDate(),
            domainModel.getPrice()
        );
    }
}
