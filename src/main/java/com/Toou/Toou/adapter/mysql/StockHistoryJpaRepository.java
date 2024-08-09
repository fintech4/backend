package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryJpaRepository extends JpaRepository<StockHistoryEntity, Long> {

	List<StockHistoryEntity> findAllByStockMetadata_StockCodeAndDateBetween(
			String stockCode, LocalDate dateFrom, LocalDate dateTo);
}
