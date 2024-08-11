package com.Toou.Toou.adapter.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "stock_history")
public class StockHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "stock_metadata_id", nullable = false)
	private Long stockMetadataId;

	@Column(name = "open_price", nullable = false)
	private Long openPrice;

	@Column(name = "high_price", nullable = false)
	private Long highPrice;

	@Column(name = "low_price", nullable = false)
	private Long lowPrice;

	@Column(name = "closing_price", nullable = false)
	private Long closingPrice;

	@Column(name = "date", nullable = false)
	private LocalDate date; // 해당 날짜
}