package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.AccountAsset;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountAssetResponse {

	private boolean ok;

	@Schema(description = "총 자산")
	private Long totalAsset;

	@Schema(description = "예수금")
	private Long deposit;

	@Schema(description = "보유주식 총액")
	private Long totalHoldingsValue;

	@Schema(description = "보유 종목 수")
	private Long totalHoldingsQuantity;
	
	@Schema(description = "총 수익률")
	private Double investmentYield;

	public static AccountAssetResponse fromDomainModel(AccountAsset domainModel) {
		return new AccountAssetResponse(
				true,
				domainModel.getTotalAsset(),
				domainModel.getDeposit(),
				domainModel.getTotalHoldingsValue(),
				domainModel.getTotalHoldingsQuantity(),
				domainModel.getInvestmentYield()
		);
	}
}
