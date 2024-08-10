package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.AccountAssetEntity;
import com.Toou.Toou.adapter.mysql.entity.HoldingStockEntity;
import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.StockOrder;
import com.Toou.Toou.domain.model.TradeType;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
import com.Toou.Toou.port.out.StockOrderPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockOrderAdapter implements StockOrderPort {

	private final AccountAssetJpaRepository accountAssetJpaRepository;
	private final HoldingStockJpaRepository holdingStockJpaRepository;


	@Override
	@Transactional
	public void orderStock(StockOrder stockOrder, AccountAsset accountAsset) {
		AccountAssetEntity accountAssetEntity = accountAssetJpaRepository.findById(accountAsset.getId())
				.orElseThrow(() -> new CustomException(
						CustomExceptionDetail.USER_NOT_FOUND)); //TODO: 이렇게 될리가 없는데 Optional이란 이유로 해야하나..

		if (stockOrder.getTradeType() == TradeType.BUY) {
			handleBuyOrder(stockOrder, accountAssetEntity);
		} else if (stockOrder.getTradeType() == TradeType.SELL) {
			handleSellOrder(stockOrder, accountAssetEntity);
		}
	}


	private void handleBuyOrder(StockOrder stockOrder, AccountAssetEntity accountAssetEntity) {
		// 현재 보유한 주식인지 확인
		HoldingStockEntity holdingStock = holdingStockJpaRepository.findByStockCodeAndAccountAssetId(
				stockOrder.getStockCode(), accountAssetEntity.getId()).orElse(null);
		;

		//TODO: buyable 이용해서 구매 가능한지 판단 -> 어댑터 사용?

		// 주문 금액 계산 및 deposit 감소
		Long totalCost = calculateTotalCost(stockOrder);
		accountAssetEntity.setDeposit(accountAssetEntity.getDeposit() - totalCost);

		// 보유 주식이 없다면 새로 추가
		if (holdingStock == null) {
			holdingStock = new HoldingStockEntity(stockOrder, accountAssetEntity);
			holdingStockJpaRepository.save(holdingStock);
		} else {
			// 이미 보유한 주식이라면 수량과 평균 매입가, 평가 금액 업데이트
			Long newQuantity = holdingStock.getQuantity() + stockOrder.getOrderQuantity();
			Long newValuation = holdingStock.getValuation() + totalCost;
			Long newAveragePurchasePrice = newValuation / newQuantity;

			holdingStock.setQuantity(newQuantity);
			holdingStock.setAveragePurchasePrice(newAveragePurchasePrice);
			holdingStock.setValuation(newValuation);
			holdingStock.setCurrentPrice(stockOrder.getStockPrice());
			holdingStockJpaRepository.save(holdingStock);
		}
	}

	private void handleSellOrder(StockOrder stockOrder, AccountAssetEntity accountAssetEntity) {
		// 현재 보유한 주식을 가져옴
		HoldingStockEntity holdingStock = holdingStockJpaRepository.findByStockCodeAndAccountAssetId(
						stockOrder.getStockCode(), accountAssetEntity.getId()).stream().findFirst()
				.orElseThrow(() -> new CustomException(CustomExceptionDetail.NO_HOLDING_STOCK));

		// 판매 가능한 수량인지 확인
		if (holdingStock.getQuantity() < stockOrder.getOrderQuantity()) {
			throw new CustomException(CustomExceptionDetail.WRONG_SELL_QUANTITY);
		}

		// 판매 금액 계산 및 deposit 증가
		Long totalSale = calculateTotalCost(stockOrder);
		accountAssetEntity.setDeposit(accountAssetEntity.getDeposit() + totalSale);

		// 주식 수량 및 평가 금액 업데이트
		holdingStock.setQuantity(holdingStock.getQuantity() - stockOrder.getOrderQuantity());
		holdingStock.setValuation(holdingStock.getValuation() - totalSale);
		if (holdingStock.getQuantity() == 0) {
			holdingStockJpaRepository.delete(holdingStock);
		} else {
			holdingStockJpaRepository.save(holdingStock);
		}
	}

	private Long calculateTotalCost(StockOrder stockOrder) {
		return stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
	}
}
