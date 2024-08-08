package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.out.StockHistoryPort;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockHistoryAdapter implements StockHistoryPort {

	private final StockHistoryJpaRepository stockHistoryJpaRepository;

	@Override
	public List<StockDailyHistory> findAllHistoriesBetweenDates(String stockCode,
			LocalDate dateFrom, LocalDate dateTo) {
		List<StockHistoryEntity> entities = stockHistoryJpaRepository.findAllByStockCodeAndDateBetween(
				stockCode, dateFrom, dateTo);
		return entities.stream().map(this::toDomainModel).toList();
	}

	private StockDailyHistory toDomainModel(StockHistoryEntity entity) {
		List<Long> prices = Arrays.asList(
				entity.getOpenPrice(),
				entity.getHighPrice(),
				entity.getLowPrice(),
				entity.getClosingPrice()
		);

		return new StockDailyHistory(
				entity.getId(),
				entity.getStockCode(),
				entity.getStockName(),
				prices,
				entity.getDate()
		);
	}

	private StockHistoryEntity fromDomainModel(StockDailyHistory domainModel) {
		Long openPrice = domainModel.getPrices().get(0);
		Long highPrice = domainModel.getPrices().get(1);
		Long lowPrice = domainModel.getPrices().get(2);
		Long closingPrice = domainModel.getPrices().get(3);

		return new StockHistoryEntity(
				domainModel.getId(),
				domainModel.getStockCode(),
				domainModel.getStockName(),
				openPrice,
				highPrice,
				lowPrice,
				closingPrice,
				domainModel.getDate()
		);
	}
}
