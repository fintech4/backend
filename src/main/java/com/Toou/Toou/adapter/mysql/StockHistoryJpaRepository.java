package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockHistoryJpaRepository extends JpaRepository<StockHistoryEntity, Long> {

	List<StockHistoryEntity> findAllByStockCodeAndDateBetween(String stockCode, LocalDate dateFrom,
			LocalDate dateTo);
}
