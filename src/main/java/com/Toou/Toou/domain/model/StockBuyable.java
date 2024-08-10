package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockBuyable {

	private String stockCode;
	private String stockName;
	private Long stockPrice;
	private Long deposit;
	private Long buyableQuantity;
}
