package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockHistoryEntity;
import com.Toou.Toou.adapter.mysql.entity.StockMetadataEntity;
import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.StockBuyable;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
import com.Toou.Toou.port.out.BuyableStockPort;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BuyableStockAdapter implements BuyableStockPort {

	private final AccountAssetJpaRepository accountAssetJpaRepository;
	private final StockMetadataJpaRepository stockMetadataJpaRepository;
	private final StockHistoryJpaRepository stockHistoryJpaRepository;

	@Override
	public StockBuyable getStockBuyableByStockCode(String stockCode, LocalDate buyDate,
			AccountAsset accountAsset) {
		StockMetadataEntity stockMetadata = stockMetadataJpaRepository.findByStockCode(stockCode)
				.orElseThrow(() -> new CustomException(CustomExceptionDetail.STOCK_NOT_FOUND));

		StockHistoryEntity stockHistory = stockHistoryJpaRepository.findFirstByStockMetadata_IdAndDate(
						stockMetadata.getId(),
						buyDate)
				.orElseThrow(() -> new CustomException(CustomExceptionDetail.STOCK_NOT_FOUND));

		Long closingPrice = stockHistory.getClosingPrice();
		Long deposit = accountAsset.getDeposit();
		Long buyableQuantity = deposit / closingPrice;

		return new StockBuyable(stockCode, stockMetadata.getStockName(), closingPrice, deposit,
				buyableQuantity);
	}
}
