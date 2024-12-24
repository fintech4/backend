package com.Toou.Toou.domain.dto;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;

public record StockTransactionResultDto(
    HoldingIndividualStock updatedHolding,
    AccountAsset updatedAccountAsset) {

}
