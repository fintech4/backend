package com.Toou.Toou.domain.dto;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;

/**
 * @param deleteHolding 전량 매도 시 보유 목록에서 삭제 여부
 */
public record StockTransactionResultDto(
    HoldingIndividualStock updatedHolding,
    AccountAsset updatedAccountAsset,
    boolean deleteHolding) {

}
