package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryJpaRepository extends JpaRepository<StockHistoryEntity, Long> {

	// 수정된 메서드 정의
	List<StockHistoryEntity> findAllByStockMetadataIdAndDateBetween(Long stockMetadataId,
			LocalDate dateFrom, LocalDate dateTo);

	Optional<StockHistoryEntity> findFirstByStockMetadataIdAndDate(Long stockMetadataId,
			LocalDate date);
}
