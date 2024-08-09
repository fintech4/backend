package com.Toou.Toou.adapter.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

//	private String stockCode;
//	private String stockName;
//	private Long openPrice;
//	private Long highPrice;
//	private Long lowPrice;
//	private Long closingPrice;
//	private LocalDate date; // 해당 날짜
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stock_metadata_id", nullable = false)
	private StockMetadataEntity stockMetadata;

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