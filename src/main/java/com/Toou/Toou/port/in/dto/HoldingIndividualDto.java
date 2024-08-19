package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.HoldingIndividualStock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HoldingIndividualDto {

	@Schema(description = "종목명")
	private String stockName;

	@Schema(description = "평균 매수가")
	private Long averagePurchasePrice;

	@Schema(description = "현재 가격")
	private Long currentPrice;

	@Schema(description = "보유 주식수")
	private Long quantity;

	@Schema(description = "평가금액")
	private Long valuation;

	@Schema(description = "수익률")
	private Double yield;

	public static HoldingIndividualDto fromDomainModel(HoldingIndividualStock domainModel) {
		return new HoldingIndividualDto(
				domainModel.getStockName(),
				domainModel.getAverageBuyPrice(),
				domainModel.getCurrentPrice(),
				domainModel.getQuantity(),
				domainModel.getValuation(),
				roundYieldToTwoDecimalPlaces(domainModel.getYield())
		);
	}

	private static Double roundYieldToTwoDecimalPlaces(Double yield) {
		if (yield == null) {
			return null;
		}
		// 소수점 둘째 자리에서 반올림
		return Math.round(yield * 100) / 100.0;
	}
}
