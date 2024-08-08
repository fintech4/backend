package com.Toou.Toou.port.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HoldingListResponse {

	@Schema(description = "보유 주식 별 정보")
	private List<HoldingIndividualDto> holdings;
}
