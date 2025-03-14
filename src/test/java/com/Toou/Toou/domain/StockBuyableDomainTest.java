package com.Toou.Toou.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.Toou.Toou.domain.model.StockBuyable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("StockBuyable 도메인 테스트")
public class StockBuyableDomainTest {

	@Nested
	@DisplayName("삼성전자 주식 구매 가능 개수 테스트")
	class BuyableStockTests {

		private StockBuyable stockBuyable;
		private final String stockCode = "005930";
		private final String stockName = "삼성전자";
		private final Long closingPrice = 75000L;  // 삼성전자 종가 (75,000원)
		private final Long deposit = 1_500_000L;  // 사용자 예치금 (150만 원)
		private final Long expectedBuyableQuantity = deposit / closingPrice; // 20주 구매 가능

		@BeforeEach
		void setUp() {
			stockBuyable = StockBuyable.of(stockCode, stockName, closingPrice, deposit);
		}

		@Test
		@DisplayName("도메인 객체가 정상적으로 생성되는지 확인")
		void testStockBuyableCreation() {
			assertThat(stockBuyable).isNotNull();
			assertThat(stockBuyable.getStockCode()).isEqualTo(stockCode);
			assertThat(stockBuyable.getStockName()).isEqualTo(stockName);
			assertThat(stockBuyable.getStockPrice()).isEqualTo(closingPrice);
			assertThat(stockBuyable.getDeposit()).isEqualTo(deposit);
			assertThat(stockBuyable.getBuyableQuantity()).isEqualTo(expectedBuyableQuantity);
		}

		@Test
		@DisplayName("예치금이 충분할 때 구매 가능 개수 계산 확인")
		void testBuyableQuantityCalculation() {
			assertThat(stockBuyable.getBuyableQuantity()).isEqualTo(20L);
		}

		@Test
		@DisplayName("예치금이 부족할 경우 구매 가능 개수가 0인지 확인")
		void testZeroBuyableQuantity() {
			Long insufficientDeposit = 50_000L;
			StockBuyable insufficientStockBuyable = StockBuyable.of(stockCode, stockName, closingPrice,
					insufficientDeposit);

			assertThat(insufficientStockBuyable.getBuyableQuantity()).isEqualTo(0L);
		}
	}
}