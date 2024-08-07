package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.StockDailyHistory;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockDailyHistoryDto {

	private LocalDate date; // 해당 날짜
	private List<Long> prices; // 해당 날짜의 종가(KRW)

	public static StockDailyHistoryDto fromDomainModel(StockDailyHistory domainModel) {
		return new StockDailyHistoryDto(
				domainModel.getDate(),
				domainModel.getPrices()
		);
	}
}
