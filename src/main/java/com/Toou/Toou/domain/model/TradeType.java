package com.Toou.Toou.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TradeType {
	BUY("buy"),
	SELL("sell");

	private final String type;

	TradeType(String type) {
		this.type = type;
	}

	@JsonValue
	public String value() {
		return type;
	}
}
