package com.Toou.Toou.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.Toou.Toou.domain.dto.StockTransactionResultDto;
import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.domain.model.OAuthProvider;
import com.Toou.Toou.domain.model.StockOrder;
import com.Toou.Toou.domain.model.TradeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("StockOrder 도메인 테스트")
public class StockOrderDomainTest {

  @Nested
  @DisplayName("주식 매수 성공 테스트케이스 (보유 종목 0개)")
  class BuyStockTests {

    private StockOrder stockOrder;
    private AccountAsset accountAsset;
    private final Long stockPrice = 1000L;
    private final Long orderQuantity = 1L;

    @BeforeEach
    void setUp() {
      final Long totalAsset = 1000000L;
      final Long deposit = 1000000L;
      final Long totalHoldingsValue = 0L;
      final Long totalHoldingsQuantity = 0L;
      final Double investmentYield = 0.0;
      final Long totalPrincipal = 0L;

      accountAsset = new AccountAsset(
          1L, OAuthProvider.KAKAO, "id1",
          totalAsset, deposit, totalHoldingsValue,
          totalHoldingsQuantity, investmentYield, totalPrincipal
      );

      stockOrder = new StockOrder(
          "s1", "sname1",
          stockPrice, orderQuantity, TradeType.BUY, accountAsset
      );
    }

    @Test
    @DisplayName("DTO 결과 null 아님")
    void testBuyStockResultDtoNotNull() {
      // when
      StockTransactionResultDto resultDto = stockOrder.handleBuy(accountAsset, null);

      // then
      assertThat(resultDto).isNotNull();
      assertThat(resultDto.updatedAccountAsset()).isNotNull();
      assertThat(resultDto.updatedHolding()).isNotNull();
    }

    @Test
    @DisplayName("AccountAsset 결과")
    void testBuyStockAccountAssetUpdate() {
      // when
      StockTransactionResultDto resultDto = stockOrder.handleBuy(accountAsset, null);
      AccountAsset updatedAccountAsset = resultDto.updatedAccountAsset();

      // then
      assertThat(updatedAccountAsset.getDeposit()).isEqualTo(
          accountAsset.getDeposit() - (stockPrice * orderQuantity));
      assertThat(updatedAccountAsset.getTotalHoldingsValue()).isEqualTo(
          stockPrice * orderQuantity);
      assertThat(updatedAccountAsset.getTotalHoldingsQuantity()).isEqualTo(orderQuantity);
      assertThat(updatedAccountAsset.getTotalAsset()).isEqualTo(
          updatedAccountAsset.getDeposit() + updatedAccountAsset.getTotalHoldingsValue());
    }

    @Test
    @DisplayName("HoldingIndividualStock 결과")
    void testBuyStockHoldingIndividualStockUpdate() {
      // when
      StockTransactionResultDto resultDto = stockOrder.handleBuy(accountAsset, null);
      HoldingIndividualStock updatedHolding = resultDto.updatedHolding();

      // then
      assertThat(updatedHolding).isNotNull();
      assertThat(updatedHolding.getStockCode()).isEqualTo(stockOrder.getStockCode());
      assertThat(updatedHolding.getStockName()).isEqualTo(stockOrder.getStockName());
      assertThat(updatedHolding.getQuantity()).isEqualTo(orderQuantity);
      assertThat(updatedHolding.getAverageBuyPrice()).isEqualTo(stockPrice);
      assertThat(updatedHolding.getValuation()).isEqualTo(stockPrice * orderQuantity);
      assertThat(updatedHolding.getYield()).isEqualTo(0.0); // 신규 매수이므로 수익률은 0
    }

    @Test
    @DisplayName("DeleteHolding 값 false")
    void testBuyStockDeleteHolding() {
      // when
      StockTransactionResultDto resultDto = stockOrder.handleBuy(accountAsset, null);

      // then
      assertThat(resultDto.deleteHolding()).isFalse();
    }
  }

}
