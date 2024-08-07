package com.Toou.Toou.port.in.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockSearchListResponse {

	private boolean ok;
	private List<StockMetadataDto> stockSearchList;
}
