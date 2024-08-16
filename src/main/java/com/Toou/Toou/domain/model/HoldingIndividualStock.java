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
	private Long averageBuyPrice;     // 평균 매수가
	private Long currentPrice;             // 현재 가격
	private Long quantity;                 // 보유 주식수
	private Long valuation;                // 평가금액
	private Double yield;
	private Long accountAssetId;
	private Long principal;                 //투자 원금

	public HoldingIndividualStock(StockOrder stockOrder, long accountAssetId) {
		this.stockCode = stockOrder.getStockCode();
		this.stockName = stockOrder.getStockName();
		this.averageBuyPrice = stockOrder.getStockPrice();
		this.currentPrice = stockOrder.getStockPrice();
		this.quantity = stockOrder.getOrderQuantity();
		this.valuation = stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
		this.yield = 0.0; // 초기 수익률은 0으로 설정
		this.accountAssetId = accountAssetId;
		this.principal = stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
	}

	public HoldingIndividualStock updateWhenBuyStock(StockOrder stockOrder) {
		long totalOrderValue = stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
		Long newQuantity = this.quantity + stockOrder.getOrderQuantity();
		Long newValuation = this.valuation + totalOrderValue;
		Long newPrincipal = this.principal + totalOrderValue;
		Long newAverageBuyPrice = newPrincipal / newQuantity;
		Double newYield =
				((double) (this.currentPrice - newAverageBuyPrice) / newAverageBuyPrice) * 100;
		return new HoldingIndividualStock(
				this.id,
				this.stockCode,
				this.stockName,
				newAverageBuyPrice,
				this.currentPrice,
				newQuantity,
				newValuation,
				newYield,
				this.accountAssetId,
				newPrincipal
		);
	}

	public HoldingIndividualStock updateWhenSellStock(StockOrder stockOrder) {
		long totalOrderValue = stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
		Long newQuantity = this.quantity - stockOrder.getOrderQuantity();
		Long newValuation = this.valuation - totalOrderValue;
		Long newPrincipal = this.principal - totalOrderValue;
		Long newAverageBuyPrice = newPrincipal / newQuantity;
		Double newYield =
				((double) (this.currentPrice - newAverageBuyPrice) / newAverageBuyPrice) * 100;
		return new HoldingIndividualStock(
				this.id,
				this.stockCode,
				this.stockName,
				newAverageBuyPrice,
				this.currentPrice,
				newQuantity,
				newValuation,
				newYield,
				this.accountAssetId,
				newPrincipal
		);
	}

	public HoldingIndividualStock updateWithNewHoldingsData(Long newPrice) {
		Long newValuation = newPrice * this.quantity;
		Double newYield =
				((double) (this.currentPrice - this.averageBuyPrice) / this.averageBuyPrice) * 100;

		return new HoldingIndividualStock(
				this.id,
				this.stockCode,
				this.stockName,
				this.averageBuyPrice,
				newPrice,
				this.quantity,
				newValuation,
				newYield,
				this.accountAssetId,
				this.principal
		);
	}
}