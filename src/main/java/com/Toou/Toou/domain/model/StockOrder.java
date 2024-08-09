package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StockOrder {

	private String stockCode;
	private String stockName;
	private Long stockPrice;
	private Long orderQuantity;
	private TradeType tradeType;
	private AccountAsset accountAsset;
}
