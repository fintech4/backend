package com.Toou.Toou.port.in;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.port.in.dto.AccountAssetResponse;
import com.Toou.Toou.port.in.dto.HoldingIndividualDto;
import com.Toou.Toou.port.in.dto.HoldingListResponse;
import com.Toou.Toou.port.in.dto.OrderableQuantityResponse;
import com.Toou.Toou.port.in.dto.StockOrderRequest;
import com.Toou.Toou.port.in.dto.VoidResponse;
import com.Toou.Toou.usecase.AccountAssetUseCase;
import com.Toou.Toou.usecase.AccountHoldingUseCase;
import com.Toou.Toou.usecase.BuyableStockUseCase;
import com.Toou.Toou.usecase.SellableStockUseCase;
import com.Toou.Toou.usecase.StockOrderUseCase;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountAssetUseCase accountAssetUseCase;
	private final AccountHoldingUseCase accountHoldingUseCase;
	private final SellableStockUseCase sellableStockUseCase;
	private final BuyableStockUseCase buyableStockUseCase;
	private final StockOrderUseCase stockOrderUseCase;

	@GetMapping("/assets")
	ResponseEntity<AccountAssetResponse> asset(
			@CookieValue(value = "providerId", required = true) String providerId) {
		AccountAsset accountAsset = getAccountAssetByProviderId(providerId);
		AccountAssetResponse response = AccountAssetResponse.fromDomainModel(accountAsset);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/holdings")
	ResponseEntity<HoldingListResponse> holdingListStock(
			@CookieValue(value = "providerId", required = true) String providerId) {
		LocalDate todayDate = getTodayDate();
		AccountHoldingUseCase.Input input = new AccountHoldingUseCase.Input(providerId, todayDate);
		AccountHoldingUseCase.Output output = accountHoldingUseCase.execute(input);
		HoldingListResponse response = new HoldingListResponse(true, output.getHoldings().stream().map(
				HoldingIndividualDto::fromDomainModel).toList());
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/stocks/{stockCode}/sellable")
	ResponseEntity<OrderableQuantityResponse> sellableStockCount(
			@CookieValue(value = "providerId") String providerId,
			@PathVariable String stockCode) {
		AccountAsset accountAsset = getAccountAssetByProviderId(providerId);
		SellableStockUseCase.Input input = new SellableStockUseCase.Input(stockCode,
				providerId);
		SellableStockUseCase.Output output = sellableStockUseCase.execute(input);
		OrderableQuantityResponse response = OrderableQuantityResponse.fromDomainModel(
				output.getStockSellable());
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/stocks/{stockCode}/buyable")
	ResponseEntity<OrderableQuantityResponse> buyableStockCount(
			@CookieValue(value = "providerId") String providerId,
			@PathVariable String stockCode) {
		AccountAsset accountAsset = getAccountAssetByProviderId(providerId);
		LocalDate todayDate = getTodayDate();
		BuyableStockUseCase.Input input = new BuyableStockUseCase.Input(stockCode, todayDate,
				providerId);
		BuyableStockUseCase.Output output = buyableStockUseCase.execute(input);
		OrderableQuantityResponse response = OrderableQuantityResponse.fromDomainModel(
				output.getStockBuyable());
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/stocks/{stockCode}/order")
	ResponseEntity<VoidResponse> orderStock(
			@CookieValue(value = "providerId") String providerId,
			@Valid @RequestBody StockOrderRequest request,
			@PathVariable String stockCode) {
		LocalDate todayDate = getTodayDate();
		StockOrderUseCase.Input input = new StockOrderUseCase.Input(stockCode, todayDate,
				providerId, request.getTradeType(), request.getOrderQuantity());
		StockOrderUseCase.Output output = stockOrderUseCase.execute(input);
		VoidResponse response = new VoidResponse(true);
		return ResponseEntity.ok().body(response);
	}

	private static boolean isValidTradeType(LocalDate dateFrom, LocalDate dateTo) {
		return dateFrom.isEqual(dateTo) || dateFrom.isBefore(dateTo);
	}

	private AccountAsset getAccountAssetByProviderId(String providerId) {
		AccountAssetUseCase.Input input = new AccountAssetUseCase.Input(providerId);
		AccountAssetUseCase.Output output = accountAssetUseCase.execute(input);
		return output.getAccountAsset();
	}

	private LocalDate getTodayDate() {
		return LocalDate.now();
	}
}
