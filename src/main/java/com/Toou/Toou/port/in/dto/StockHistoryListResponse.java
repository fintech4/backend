package com.Toou.Toou.port.in.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class StockHistoryListResponse {

	private boolean ok;
	private Long id;
	private String stockCode;
	private String stockName;
	private Long stockNewestPrice;
	private LocalDate newestDate;
	private List<StockDailyHistoryDto> dailyHistories;
}