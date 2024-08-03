package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.out.StockHistoryPort;
import com.Toou.Toou.port.out.StockOpenApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListStockHistoryService implements ListStockHistoryUseCase {

    private final StockHistoryPort stockHistoryPort;
    private final StockOpenApiPort stockOpenApiPort;

    @Transactional
    @Override
    public Output execute(Input input) {
        List<StockDailyHistory> dailyHistories = stockHistoryPort.findAllHistoriesBetweenDates(input.companyCode, input.dateFrom, input.dateTo);
        if (!hasAllHistoriesBetweenDates(dailyHistories, input.dateFrom, input.dateTo)) {
            // TODO: (1) stockOpenApiPort를 통해, 오픈 API에서 가져온 데이터를 StockDailyHistory로 변환하고 위 dailyHistories와 합쳐준 뒤 응답해준다.
            // TODO: (2) 오픈 API에서 가져온 데이터를 StockDailyHistory로 변환한 결과에 대해 stockHistoryPort를 통해서 DB에 저장해준다.
        }
        return new Output(dailyHistories);
    }

    private boolean hasAllHistoriesBetweenDates(List<StockDailyHistory> histories, LocalDate dateFrom, LocalDate dateTo) {
        // TODO: histories에 있는 각 history들의 date를 확인해서 dateFrom~dateTo 사이 모든 날짜를 커버하는지 확인하는 로직 필요
        return false;
    }
}
