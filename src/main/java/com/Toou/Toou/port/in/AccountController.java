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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
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

	@Value("${demo}")
	boolean isDemo;

	@Value("${time-machine}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate timeMachineEndDate;

	@GetMapping("/assets")
	ResponseEntity<AccountAssetResponse> asset(
			@CookieValue(value = "kakaoId", required = true) String kakaoId) {
		AccountAsset accountAsset = getAccountAssetByKakaoId(kakaoId);
		AccountAssetResponse response = AccountAssetResponse.fromDomainModel(accountAsset);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/holdings")
	ResponseEntity<HoldingListResponse> holdingListStock(
			@CookieValue(value = "kakaoId", required = true) String kakaoId) {
		AccountHoldingUseCase.Input input = new AccountHoldingUseCase.Input(kakaoId);
		AccountHoldingUseCase.Output output = accountHoldingUseCase.execute(input);
		HoldingListResponse response = new HoldingListResponse(true, output.getHoldings().stream().map(
				HoldingIndividualDto::fromDomainModel).toList());
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/stocks/{stockCode}/sellable")
	ResponseEntity<OrderableQuantityResponse> sellableStockCount(
			@CookieValue(value = "kakaoId") String kakaoId,
			@PathVariable String stockCode) {
		AccountAsset accountAsset = getAccountAssetByKakaoId(kakaoId);
		SellableStockUseCase.Input input = new SellableStockUseCase.Input(stockCode,
				kakaoId);
		SellableStockUseCase.Output output = sellableStockUseCase.execute(input);
		OrderableQuantityResponse response = OrderableQuantityResponse.fromDomainModel(
				output.getStockSellable());
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/stocks/{stockCode}/buyable")
	ResponseEntity<OrderableQuantityResponse> buyableStockCount(
			@CookieValue(value = "kakaoId") String kakaoId,
			@PathVariable String stockCode) {
		AccountAsset accountAsset = getAccountAssetByKakaoId(kakaoId);
		LocalDate todayDate = getTodayDate();
		BuyableStockUseCase.Input input = new BuyableStockUseCase.Input(stockCode, todayDate,
				kakaoId);
		BuyableStockUseCase.Output output = buyableStockUseCase.execute(input);
		OrderableQuantityResponse response = OrderableQuantityResponse.fromDomainModel(
				output.getStockBuyable());
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/stocks/{stockCode}/order")
	ResponseEntity<VoidResponse> orderStock(
			@CookieValue(value = "kakaoId") String kakaoId,
			@Valid @RequestBody StockOrderRequest request,
			@PathVariable String stockCode) {
		LocalDate todayDate = getTodayDate();
		StockOrderUseCase.Input input = new StockOrderUseCase.Input(stockCode, todayDate,
				kakaoId, request.getTradeType(), request.getOrderQuantity());
		StockOrderUseCase.Output output = stockOrderUseCase.execute(input);
		VoidResponse response = new VoidResponse(true);
		return ResponseEntity.ok().body(response);
	}

	private static boolean isValidTradeType(LocalDate dateFrom, LocalDate dateTo) {
		return dateFrom.isEqual(dateTo) || dateFrom.isBefore(dateTo);
	}

	private AccountAsset getAccountAssetByKakaoId(String kakaoId) {
		AccountAssetUseCase.Input input = new AccountAssetUseCase.Input(kakaoId);
		AccountAssetUseCase.Output output = accountAssetUseCase.execute(input);
		return output.getAccountAsset();
	}

	private LocalDate getTodayDate() {
		return isDemo ? timeMachineEndDate : LocalDate.now();
	}
}
