package com.Toou.Toou.domain.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockDailyHistory {

	private Long id;
	private String stockCode;
	private String stockName;
	private String market;
	private List<Long> prices; // 시가, 고가, 저가, 종가
	private LocalDate date; // 해당 날짜
}