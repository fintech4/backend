package com.Toou.Toou.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionDetail {
	STOCK_NOT_FOUND("stock_not_found", "해당하는 주식 종목을 찾을 수 없습니다"),
	USER_NOT_FOUND("user_not_found", "해당하는 id로 사용자를 찾을 수 없습니다");

	private final String type;
	private final String message;

}