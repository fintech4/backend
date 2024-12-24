package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.dto.StockTransactionResultDto;
import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.domain.model.StockOrder;
import com.Toou.Toou.domain.model.TradeType;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
import com.Toou.Toou.port.out.AccountAssetPort;
import com.Toou.Toou.port.out.HoldingStockPort;
import com.Toou.Toou.port.out.StockHistoryPort;
import com.Toou.Toou.port.out.StockMetadataPort;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockOrderService implements StockOrderUseCase {

  private final AccountAssetPort accountAssetPort;
  private final HoldingStockPort holdingStockPort;
  private final StockMetadataPort stockMetadataPort;
  private final StockHistoryPort stockHistoryPort;


  @Override
  public Output execute(Input input) {
    AccountAsset accountAsset = accountAssetPort.findByProviderId(input.providerId);
    StockMetadata stockMetadata = fetchStockMetadata(input.stockCode);
    StockDailyHistory stockDailyHistory = fetchStockDailyHistory(
        stockMetadata.getId(),
        input.orderDate
    );
    StockOrder stockOrder = new StockOrder(
        input.stockCode,
        stockMetadata.getStockName(),
        stockDailyHistory.getClosingPrice(),
        input.orderQuantity,
        input.tradeType,
        accountAsset
    );
    HoldingIndividualStock holdingIndividualStock = holdingStockPort.findHoldingByStockCodeAndAssetId(
        input.stockCode, accountAsset.getId());

    if (input.tradeType == TradeType.BUY) {
      StockTransactionResultDto stockTransactionResultDto = stockOrder.handleBuy(accountAsset,
          holdingIndividualStock);
      saveBuyResults(stockTransactionResultDto);
    } else if (input.tradeType == TradeType.SELL) {
      StockTransactionResultDto stockTransactionResultDto = stockOrder.handleSell(accountAsset,
          holdingIndividualStock);
      saveSellResults(stockTransactionResultDto);
    }

    return new Output();
  }

  private StockMetadata fetchStockMetadata(String stockCode) {
    StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(stockCode);
    if (stockMetadata == null) {
      throw new CustomException(CustomExceptionDetail.STOCK_NOT_FOUND);
    }
    return stockMetadata;
  }

  private StockDailyHistory fetchStockDailyHistory(Long stockId, LocalDate orderDate) {
    StockDailyHistory stockDailyHistory = stockHistoryPort.findStockHistoryByDate(
        stockId,
        orderDate
    );
    if (stockDailyHistory == null) {
      throw new CustomException(CustomExceptionDetail.STOCK_HISTORY_NOT_FOUND);
    }
    return stockDailyHistory;
  }

  private void saveBuyResults(StockTransactionResultDto stockTransactionResultDto) {
    holdingStockPort.save(stockTransactionResultDto.updatedHolding());
    accountAssetPort.saveAsset(stockTransactionResultDto.updatedAccountAsset());
  }

  private void saveSellResults(StockTransactionResultDto stockTransactionResultDto) {
    HoldingIndividualStock holdingIndividualStock = stockTransactionResultDto.updatedHolding();
    if (stockTransactionResultDto.deleteHolding()) {
      holdingStockPort.delete(holdingIndividualStock);
    } else {
      holdingStockPort.save(holdingIndividualStock);
    }
    accountAssetPort.saveAsset(stockTransactionResultDto.updatedAccountAsset());
  }
}
