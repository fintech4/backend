package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.port.out.StockHistoryPort;
import com.Toou.Toou.port.out.StockMetadataPort;
import com.Toou.Toou.port.out.StockOpenApiPort;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListStockHistoryService implements ListStockHistoryUseCase {

	private static final Logger log = LoggerFactory.getLogger(ListStockHistoryService.class);
	private final StockHistoryPort stockHistoryPort;
	private final StockOpenApiPort stockOpenApiPort;
	private final StockMetadataPort stockMetadataPort;

	@Transactional
	@Override
	public Output execute(Input input) {
		StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(input.stockCode);
		List<StockDailyHistory> dailyHistories = stockHistoryPort.findAllHistoriesBetweenDates(
				stockMetadata.getId(), input.dateFrom, input.dateTo);

		LocalDate lastDate = dailyHistories.stream()
				.map(StockDailyHistory::getDate)
				.max(LocalDate::compareTo)
				.orElse(input.dateFrom);

		if (lastDate.isBefore(input.dateTo)) {
			// lastDate의 다음 날부터 dateTo까지의 데이터를 오픈 API에서 가져옴
			List<StockDailyHistory> externalHistories = stockOpenApiPort.findAllHistoriesBetweenDates(
					stockMetadata.getId(),
					stockMetadata.getStockName(), lastDate.plusDays(1), input.dateTo);
			// 4. 가져온 데이터가 비어 있지 않으면 리스트에 추가하고 데이터베이스에도 저장
			if (!externalHistories.isEmpty()) {
				externalHistories.forEach(stockHistoryPort::save);
				dailyHistories.addAll(externalHistories);

				// 중복된 데이터가 있을 수 있으므로 정렬 및 중복 제거
				dailyHistories = dailyHistories.stream()
						.distinct()
						.sorted(Comparator.comparing(StockDailyHistory::getDate))
						.collect(Collectors.toList());
			}
		}
		return new Output(dailyHistories, stockMetadata.getStockCode(), stockMetadata.getStockName(),
				stockMetadata.getMarket());
	}
//
//	private boolean hasAllHistoriesBetweenDates(List<StockDailyHistory> histories, LocalDate dateFrom,
//			LocalDate dateTo) {
//		// TODO: histories에 있는 각 history들의 date를 확인해서 dateFrom~dateTo 사이 모든 날짜를 커버하는지 확인하는 로직 필요
//		return false;
//	}
}
