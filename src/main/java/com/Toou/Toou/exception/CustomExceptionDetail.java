package com.Toou.Toou.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionDetail {
	STOCK_NOT_FOUND("stock_not_found", "해당하는 주식 종목을 찾을 수 없습니다"),
	STOCK_HISTORY_NOT_FOUND("stock_history_not_found", "종목의 가격을 찾을 수 없습니다"),
	USER_NOT_FOUND("user_not_found", "해당하는 id로 사용자를 찾을 수 없습니다"),
	WRONG_BUY_ORDER("wrong_buy_order", "유효하지 않은 매수 주문입니다"),
	NO_HOLDING_STOCK("no_holding_stock", "보유하지 않은 종목입니다"),
	WRONG_SELL_QUANTITY("wrong_sell_quantity", "매도 가능한 수량이 아닙니다");

	private final String type;
	private final String message;

}