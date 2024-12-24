package com.Toou.Toou.domain.model;

import com.Toou.Toou.domain.dto.StockTransactionResultDto;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StockOrder {

  private String stockCode;
  private String stockName;
  private Long stockPrice;
  private Long orderQuantity;
  private TradeType tradeType;
  private AccountAsset accountAsset;


  public StockTransactionResultDto handleBuy(AccountAsset accountAsset,
      HoldingIndividualStock holdingIndividualStock) {
    validateBuy(accountAsset.getDeposit());
    boolean isFirstBuy = holdingIndividualStock == null;
    HoldingIndividualStock updatedHolding = isFirstBuy
        ? new HoldingIndividualStock(this, accountAsset.getId())
        : holdingIndividualStock.updateWhenBuyStock(this);
    AccountAsset updatedAccountAsset = accountAsset.updateWhenBuyStock(this, isFirstBuy);
    return new StockTransactionResultDto(updatedHolding, updatedAccountAsset, false);
  }

  public StockTransactionResultDto handleSell(AccountAsset accountAsset,
      HoldingIndividualStock holdingIndividualStock) {
    validateSell(holdingIndividualStock);
    boolean isLastStockSold = holdingIndividualStock.getQuantity().equals(this.getOrderQuantity());
    HoldingIndividualStock updatedHolding = holdingIndividualStock.updateWhenSellStock(this);
    AccountAsset updatedAccountAsset = accountAsset.updateWhenSellStock(this, isLastStockSold);
    return new StockTransactionResultDto(updatedHolding, updatedAccountAsset, isLastStockSold);
  }

  public void validateBuy(Long deposit) {
    Long buyableQuantity = deposit / this.stockPrice;
    if (buyableQuantity < this.getOrderQuantity()) {
      throw new CustomException(CustomExceptionDetail.WRONG_BUY_ORDER);
    }
  }

  public void validateSell(HoldingIndividualStock holdingIndividualStock) {
    Long sellableQuantity =
        holdingIndividualStock != null ? holdingIndividualStock.getQuantity() : 0L;
    if (sellableQuantity < this.getOrderQuantity()) {
      throw new CustomException(CustomExceptionDetail.WRONG_SELL_QUANTITY);
    }
  }
}
