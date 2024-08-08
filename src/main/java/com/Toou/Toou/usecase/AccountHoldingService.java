package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.HoldingIndividualStock;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountHoldingService implements AccountHoldingUseCase {

	private static final List<HoldingIndividualStock> DUMMY_HOLDINGS = List.of(
			new HoldingIndividualStock(1L, "A079980", "휴비스", 5000L, 5760L, 10L, 50000L, 8.07),
			new HoldingIndividualStock(2L, "A065510", "휴비츠", 6000L, 6760L, 10L, 50000L, 8.07),
			new HoldingIndividualStock(3L, "A215090", "휴센텍", 7000L, 7760L, 10L, 50000L, 8.07),
			new HoldingIndividualStock(4L, "A005010", "휴스틸", 8000L, 8760L, 10L, 50000L, 8.07)
	);

	@Transactional
	@Override
	public AccountHoldingUseCase.Output execute(AccountHoldingUseCase.Input input) {
		return new AccountHoldingUseCase.Output(DUMMY_HOLDINGS);
	}
}
