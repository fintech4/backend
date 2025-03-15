package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StockHistoryJpaRepository extends JpaRepository<StockHistoryEntity, Long> {

	// 수정된 메서드 정의
	List<StockHistoryEntity> findAllByStockMetadataIdAndDateBetween(Long stockMetadataId,
			LocalDate dateFrom, LocalDate dateTo);

	// 특정 주식 메타데이터 ID와 주어진 날짜 이전의 가장 최신 주식 기록을 찾는 메서드
	StockHistoryEntity findFirstByStockMetadataIdAndDateLessThanEqualOrderByDateDesc(
			Long stockMetadataId,
			LocalDate date);


	@Modifying
	@Transactional
	@Query(
			value =
					"INSERT IGNORE INTO stock_history (stock_metadata_id, date, open_price, high_price, low_price, closing_price) "
							+
							"VALUES (:stockMetadataId, :date, :openPrice, :highPrice, :lowPrice, :closingPrice) ",
			nativeQuery = true)
	void insertIgnore(@Param("stockMetadataId") Long stockMetadataId,
			@Param("date") LocalDate date,
			@Param("openPrice") Long openPrice,
			@Param("highPrice") Long highPrice,
			@Param("lowPrice") Long lowPrice,
			@Param("closingPrice") Long closingPrice);

	default void insertIgnore(StockHistoryEntity entity) {
		insertIgnore(
				entity.getStockMetadataId(),
				entity.getDate(),
				entity.getOpenPrice(),
				entity.getHighPrice(),
				entity.getLowPrice(),
				entity.getClosingPrice()
		);
	}
}
