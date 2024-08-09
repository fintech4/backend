package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.MarketType;
import com.Toou.Toou.domain.model.StockMetadata;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockMetadataDto {

	@Schema(description = "종목 코드")
	private String stockCode;

	@Schema(description = "종목명")
	private String stockName;

	@Schema(description = "시장")
	private MarketType market;

	public static StockMetadataDto fromDomainModel(StockMetadata domainModel) {
		return new StockMetadataDto(
				domainModel.getStockCode(),
				domainModel.getStockName(),
				domainModel.getMarket()
		);
	}
}
