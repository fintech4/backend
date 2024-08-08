package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockSellable {

	private Long id;
	private String stockCode;              // 종목 코드
	private String stockName;              // 종목명
	private Long sellableQuantity;
}
