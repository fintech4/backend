package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryJpaRepository extends JpaRepository<StockHistoryEntity, Long> {

	// 수정된 메서드 정의
	List<StockHistoryEntity> findAllByStockMetadataIdAndDateBetween(Long stockMetadataId,
			LocalDate dateFrom, LocalDate dateTo);

	// 특정 주식 메타데이터 ID와 주어진 날짜 이전의 가장 최신 주식 기록을 찾는 메서드
	StockHistoryEntity findFirstByStockMetadataIdAndDateLessThanEqualOrderByDateDesc(
			Long stockMetadataId,
			LocalDate date);
}
