package com.Toou.Toou.domain.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockDailyHistory {

	private Long id;
	private Long stockMetadataId;
	private Long openPrice;
	private Long highPrice;
	private Long lowPrice;
	private Long closingPrice;
	private LocalDate date;
}