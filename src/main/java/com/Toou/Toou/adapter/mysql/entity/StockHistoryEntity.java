package com.Toou.Toou.adapter.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "stock_history")
public class StockHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String stockCode;
	private String stockName;
	private Long openPrice;
	private Long highPrice;
	private Long lowPrice;
	private Long closingPrice;
	private LocalDate date; // 해당 날짜
}