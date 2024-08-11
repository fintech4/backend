package com.Toou.Toou.adapter.openapi.dto;

import lombok.Data;

@Data
public class StockDailyHistoryOpenApiDto {

	private String basDt;     // 날짜
	private String srtnCd;    // 종목코드
	private String itmsNm;    // 종목명
	private String mrktCtg;   // 시장구분
	private String clpr;      // 종가
	private String mkp;       // 시가
	private String hipr;      // 고가
	private String lopr;      // 저가
	private String trqu;      // 거래량
	private String trPrc;     // 거래대금
	private String lstgStCnt; // 상장주식수
	private String mrktTotAmt;// 시가총액
}