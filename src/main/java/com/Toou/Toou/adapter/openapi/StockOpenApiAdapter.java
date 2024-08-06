package com.Toou.Toou.adapter.openapi;

import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.out.StockOpenApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockOpenApiAdapter implements StockOpenApiPort {

    @Override
    public List<StockDailyHistory> findAllHistoriesBetweenDates(String companyCode, LocalDate dateFrom, LocalDate dateTo) {
        // TODO: 오픈API 찔러야함.
        // 그를 통해 응답받은 StockDailyHistoryDto를, StockDailyHistory 형태로 변환해서 응답해줘야 함
        return Collections.emptyList();
    }
}
