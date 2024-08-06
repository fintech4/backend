package com.Toou.Toou.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class StockHistoryListResponse {
    private Long id;
    private String companyCode;
    private List<StockDailyHistoryDto> dailyHistories;
}