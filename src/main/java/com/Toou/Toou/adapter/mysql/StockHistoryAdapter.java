package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.out.StockHistoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockHistoryAdapter implements StockHistoryPort {

    private final StockHistoryJpaRepository stockHistoryJpaRepository;

    @Override
    public List<StockDailyHistory> findAllHistoriesBetweenDates(String companyCode, LocalDate dateFrom, LocalDate dateTo) {
        List<StockHistoryEntity> entities = stockHistoryJpaRepository.findAllByCompanyCodeAndDateBetween(companyCode, dateFrom, dateTo);
        return entities.stream().map(this::toDomainModel).toList();
    }

    private StockDailyHistory toDomainModel(StockHistoryEntity entity) {
        return new StockDailyHistory(
            entity.getId(),
            entity.getCompanyCode(),
            entity.getPrice(),
            entity.getDate()
        );
    }

    private StockHistoryEntity fromDomainModel(StockDailyHistory domainModel) {
        return new StockHistoryEntity(
            domainModel.getId(),
            domainModel.getCompanyCode(),
            domainModel.getPrice(),
            domainModel.getDate()
        );
    }
}
