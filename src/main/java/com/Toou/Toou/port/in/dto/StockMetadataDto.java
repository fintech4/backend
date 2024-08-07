package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.StockMetadata;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockMetadataDto {

	private String stockCode;
	private String stockName;

	public static StockMetadataDto fromDomainModel(StockMetadata domainModel) {
		return new StockMetadataDto(
				domainModel.getStockCode(),
				domainModel.getStockName()
		);
	}
}
