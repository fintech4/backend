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
}