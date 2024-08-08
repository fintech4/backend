package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.StockDailyHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockDailyHistoryDto {

	@Schema(description = "해당 날짜")
	private LocalDate date;

	@Schema(description = "해당 날짜의 시가, 고가, 저가, 종가")
	private List<Long> prices;

	public static StockDailyHistoryDto fromDomainModel(StockDailyHistory domainModel) {
		return new StockDailyHistoryDto(
				domainModel.getDate(),
				domainModel.getPrices()
		);
	}
}
