package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AccountAsset {

	private Long id;
	private Long totalAsset;
	private Long deposit;
	private Long totalHoldingsValue;
	private Long totalHoldingsQuantity;
	private Double investmentYield;
}
