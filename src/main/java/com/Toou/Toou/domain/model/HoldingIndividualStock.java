package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HoldingIndividualStock {

	private Long id;
	private String stockCode;              // 종목 코드
	private String stockName;              // 종목명
	private Long averagePurchasePrice;     // 평균 매수가
	private Long currentPrice;             // 현재 가격
	private Long quantity;                 // 보유 주식수
	private Long valuation;                // 평가금액
	private Double yield;
	private Long accountAssetId;

	public HoldingIndividualStock(StockOrder stockOrder, long accountAssetId) {
		this.stockCode = stockOrder.getStockCode();
		this.stockName = stockOrder.getStockName();
		this.averagePurchasePrice = stockOrder.getStockPrice();
		this.currentPrice = stockOrder.getStockPrice();
		this.quantity = stockOrder.getOrderQuantity();
		this.valuation = stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
		this.yield = 0.0; // 초기 수익률은 0으로 설정
		this.accountAssetId = accountAssetId;
	}
}