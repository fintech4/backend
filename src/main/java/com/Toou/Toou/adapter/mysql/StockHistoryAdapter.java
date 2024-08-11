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
	public List<StockDailyHistory> findAllHistoriesBetweenDates(Long stockMetadataId,
			LocalDate dateFrom, LocalDate dateTo) {
		List<StockHistoryEntity> entities = stockHistoryJpaRepository.findAllByStockMetadataIdAndDateBetween(
				stockMetadataId, dateFrom, dateTo);
		return entities.stream()
				.map(this::toDomainModel)
				.collect(Collectors.toList());
	}

	@Override
	public StockDailyHistory findStockHistoryByDate(Long stockMetadataId, LocalDate date) {
		//해당 날짜 전의 최근 값으로 매수/매도
		StockHistoryEntity entity = stockHistoryJpaRepository.findFirstByStockMetadataIdAndDateLessThanEqualOrderByDateDesc(
				stockMetadataId, date);
		return entity == null ? null : toDomainModel(entity);
	}

	@Override
	public StockDailyHistory save(StockDailyHistory stockDailyHistory) {
		StockHistoryEntity entity = toEntity(stockDailyHistory);
		StockHistoryEntity savedEntity = stockHistoryJpaRepository.save(entity);
		return toDomainModel(savedEntity);
	}


	private StockDailyHistory toDomainModel(StockHistoryEntity entity) {
		return new StockDailyHistory(
				entity.getId(),
				entity.getStockMetadataId(),
				entity.getOpenPrice(),
				entity.getHighPrice(),
				entity.getLowPrice(),
				entity.getClosingPrice(),
				entity.getDate()
		);
	}

	private StockHistoryEntity toEntity(StockDailyHistory stockDailyHistory) {
		StockHistoryEntity entity = new StockHistoryEntity();
		entity.setStockMetadataId(stockDailyHistory.getStockMetadataId());
		entity.setOpenPrice(stockDailyHistory.getOpenPrice());
		entity.setHighPrice(stockDailyHistory.getHighPrice());
		entity.setLowPrice(stockDailyHistory.getLowPrice());
		entity.setClosingPrice(stockDailyHistory.getClosingPrice());
		entity.setDate(stockDailyHistory.getDate());

		return entity;
	}
}
