package com.Toou.Toou.port.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class StockHistoryListResponse {

	private boolean ok;

	@Schema(description = "주식 pk")
	private Long id;

	@Schema(description = "종목 코드")
	private String stockCode;

	@Schema(description = "종목명")
	private String stockName;

	@Schema(description = "시장")
	private String market;

	@Schema(description = "최신 주가 데이터 업데이트 날짜에서의 주가")
	private Long stockNewestPrice;

	@Schema(description = "최신 주가 데이터 업데이트 날짜")
	private LocalDate newestDate;

	@Schema(description = "매일 차트 정보")
	private List<StockDailyHistoryDto> dailyHistories;
}