package com.Toou.Toou.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.domain.model.MarketType;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.domain.model.StockSellable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("StockSellable 도메인 테스트")
public class StockSellableDomainTest {

	@Nested
	@DisplayName("삼성전자 주식 판매 가능 개수 테스트")
	class ValidStockSellableTests {

		@Test
		@DisplayName("삼성전자 주식을 보유한 경우 sellableQuantity가 정상적으로 설정되어야 한다.")
		void shouldCreateStockSellableWithHoldingStock() {
			// Given
			HoldingIndividualStock holdingStock = new HoldingIndividualStock(
					1L, "005930", "삼성전자", 70000L, 75000L, 100L, 7_500_000L, 7.1, 1L, 7_000_000L
			);
			StockMetadata stockMetadata = new StockMetadata(1L, "005930", "삼성전자", MarketType.KOSPI);

			// When
			StockSellable stockSellable = StockSellable.of(holdingStock, stockMetadata.getStockCode(),
					stockMetadata.getStockName());

			// Then
			assertThat(stockSellable.getStockCode()).isEqualTo("005930");
			assertThat(stockSellable.getStockName()).isEqualTo("삼성전자");
			assertThat(stockSellable.getSellableQuantity()).isEqualTo(100L);
		}

		@Test
		@DisplayName("삼성전자 주식을 보유하지 않은 경우 sellableQuantity가 0이 되어야 한다.")
		void shouldCreateStockSellableWithZeroQuantityIfNoHoldingStock() {
			// Given
			HoldingIndividualStock holdingStock = null;  // 주식 없음
			StockMetadata stockMetadata = new StockMetadata(1L, "005930", "삼성전자", MarketType.KOSPI);

			// When
			StockSellable stockSellable = StockSellable.of(holdingStock, stockMetadata.getStockCode(),
					stockMetadata.getStockName());

			// Then
			assertThat(stockSellable.getStockCode()).isEqualTo("005930");
			assertThat(stockSellable.getStockName()).isEqualTo("삼성전자");
			assertThat(stockSellable.getSellableQuantity()).isEqualTo(0L);
		}
	}
}
