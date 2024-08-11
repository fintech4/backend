package com.Toou.Toou.adapter.openapi.dto;

import java.util.List;
import lombok.Data;

@Data
public class StockOpenApiResponse {

	private ResponseBody body;  // body 필드를 추가합니다.

	@Data
	public static class ResponseBody {

		private int numOfRows;
		private int pageNo;
		private int totalCount;
		private Items items;
	}

	@Data
	public static class Items {

		private List<StockDailyHistoryOpenApiDto> item;
	}
}
