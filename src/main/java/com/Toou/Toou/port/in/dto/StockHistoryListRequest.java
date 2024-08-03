package com.Toou.Toou.port.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StockHistoryListRequest { // RequestBody를 쓸 거면, 이렇게 Request도 클래스를 만들어서 사용하면 됨 (RequestBody가 아니라 컨트롤러에서 RequestParam을 쓰고 있으므로 이 클래스는 미사용했음)
    private String companyCode;
    private LocalDate dateFrom; // 구간 시작 날짜
    private LocalDate dateTo; // 구간 끝 날짜
}