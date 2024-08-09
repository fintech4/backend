package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.out.StockHistoryPort;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockHistoryAdapter implements StockHistoryPort {

	private final StockHistoryJpaRepository stockHistoryJpaRepository;

	@Override
	public List<StockDailyHistory> findAllHistoriesBetweenDates(String stockCode,
			LocalDate dateFrom, LocalDate dateTo) {
		List<StockHistoryEntity> entities = stockHistoryJpaRepository.findAllByStockMetadata_StockCodeAndDateBetween(
				stockCode, dateFrom, dateTo);
		return entities.stream()
				.map(this::toDomainModel)
				.collect(Collectors.toList());
	}

	private StockDailyHistory toDomainModel(StockHistoryEntity entity) {
		return new StockDailyHistory(
				entity.getId(),
				entity.getStockMetadata().getStockCode(),
				entity.getStockMetadata().getStockName(),
				List.of(entity.getOpenPrice(), entity.getHighPrice(), entity.getLowPrice(),
						entity.getClosingPrice()),
				entity.getDate()
		);
	}
}
