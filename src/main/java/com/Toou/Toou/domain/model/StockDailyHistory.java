package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockDailyHistory {
    private Long id;
    private String companyCode;
    private Long price; // 해당 날짜의 종가(KRW)
    private LocalDate date; // 해당 날짜
}