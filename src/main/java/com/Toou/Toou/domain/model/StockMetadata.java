package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockMetadata {

	private Long id;
	private String stockCode;
	private String stockName;
	private MarketType market;
}
