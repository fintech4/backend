package com.Toou.Toou.adapter.openapi.dto;

import java.util.List;
import lombok.Data;

@Data
public class StockOpenApiResponse {

	private Response response;

	@Data
	public static class Response {

		private ResponseHeader header;
		private ResponseBody body;
	}

	@Data
	public static class ResponseHeader {

		private String resultCode;
		private String resultMsg;
	}

	@Data
	public static class ResponseBody {

		private int numOfRows;
		private int pageNo;
		private int totalCount;
		private ItemsWrapper items;
	}

	@Data
	public static class ItemsWrapper {

		private List<StockDailyHistoryOpenApiDto> item;
	}
}
