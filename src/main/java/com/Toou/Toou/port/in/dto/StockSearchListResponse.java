package com.Toou.Toou.port.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockSearchListResponse {

	private boolean ok;

	@Schema(description = "검색어로 조회된 종목명 리스트")
	private List<StockMetadataDto> stockSearchList;
}
