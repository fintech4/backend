package com.Toou.Toou.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MarketType {
	KOSPI("KOSPI"),
	KOSDAQ("KOSDAQ"),
	KONEX("KONEX");

	private final String value;

	MarketType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

}
