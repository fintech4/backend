package com.Toou.Toou.port.in;

import com.Toou.Toou.domain.model.UserAccount;
import com.Toou.Toou.port.in.dto.AccountAssetResponse;
import com.Toou.Toou.port.in.dto.HoldingIndividualDto;
import com.Toou.Toou.port.in.dto.HoldingListResponse;
import com.Toou.Toou.port.in.dto.OrderableQuantityResponse;
import com.Toou.Toou.port.in.dto.UserAccountResponse;
import com.Toou.Toou.usecase.AccountAssetUseCase;
import com.Toou.Toou.usecase.AccountHoldingUseCase;
import com.Toou.Toou.usecase.BuyableStockUseCase;
import com.Toou.Toou.usecase.SellableStockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	private static final UserAccount DUMMY_USER_ACCOUNT = new UserAccount(
			1L, "kakaoId", "test@example.com", "testuser", ""
	);


	@GetMapping("/user")
	ResponseEntity<UserAccountResponse> userAccount() {
		UserAccountResponse response = UserAccountResponse.fromDomainModel(DUMMY_USER_ACCOUNT);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/assets")
	ResponseEntity<AccountAssetResponse> asset() {
		AccountAssetUseCase.Input input = new AccountAssetUseCase.Input(DUMMY_USER_ACCOUNT);
		AccountAssetUseCase.Output output = accountAssetUseCase.execute(input);
		AccountAssetResponse response = AccountAssetResponse.fromDomainModel(output.getAccountAsset());
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/holdings")
	ResponseEntity<HoldingListResponse> holdingListStock() {
		AccountHoldingUseCase.Input input = new AccountHoldingUseCase.Input(DUMMY_USER_ACCOUNT);
		AccountHoldingUseCase.Output output = accountHoldingUseCase.execute(input);
		HoldingListResponse response = new HoldingListResponse(output.getHoldings().stream().map(
				HoldingIndividualDto::fromDomainModel).toList());
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/stocks/{stockCode}/sellable")
	ResponseEntity<OrderableQuantityResponse> sellableStockCount(@PathVariable String stockCode) {
		SellableStockUseCase.Input input = new SellableStockUseCase.Input(stockCode,
				DUMMY_USER_ACCOUNT);
		SellableStockUseCase.Output output = sellableStockUseCase.execute(input);
		OrderableQuantityResponse response = OrderableQuantityResponse.fromDomainModel(
				output.getStockSellable());
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/stocks/{stockCode}/buyable")
	ResponseEntity<OrderableQuantityResponse> buyableStockCount(@PathVariable String stockCode) {
		BuyableStockUseCase.Input input = new BuyableStockUseCase.Input(stockCode,
				DUMMY_USER_ACCOUNT);
		BuyableStockUseCase.Output output = buyableStockUseCase.execute(input);
		OrderableQuantityResponse response = OrderableQuantityResponse.fromDomainModel(
				output.getStockBuyable());
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/stocks/{stockCode}/buy")
	ResponseEntity<String> buyStock(@PathVariable String stockCode) {
		return ResponseEntity.ok("yield");
	}

	@PostMapping("/stocks/{stockCode}/sell")
	ResponseEntity<String> sellStock(@PathVariable String stockCode) {
		return ResponseEntity.ok("yield");
	}

}
