package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockSellable {

	private String stockCode;              // 종목 코드
	private String stockName;              // 종목명
	private Long sellableQuantity;

	public static StockSellable of(HoldingIndividualStock holdingStock,
			String stockCode, String stockName) {
		Long quantity = (holdingStock != null) ? holdingStock.getQuantity() : 0L;
		return new StockSellable(stockCode, stockName, quantity);
	}
}
