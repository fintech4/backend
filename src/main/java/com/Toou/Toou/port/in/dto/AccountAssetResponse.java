package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.AccountAsset;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountAssetResponse {

	private boolean ok;
	private Long totalAsset;
	private Long deposit;
	private Long totalHoldingsValue;
	private Long totalHoldingsQuantity;
	private Double investmentYield;

	public static AccountAssetResponse fromDomainModel(AccountAsset accountAsset) {
		return new AccountAssetResponse(
				true,
				accountAsset.getTotalAsset(),
				accountAsset.getDeposit(),
				accountAsset.getTotalHoldingsValue(),
				accountAsset.getTotalHoldingsQuantity(),
				accountAsset.getInvestmentYield()
		);
	}
}
