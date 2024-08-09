package com.Toou.Toou.adapter.mysql;


import com.Toou.Toou.adapter.mysql.entity.AccountAssetEntity;
import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.port.out.AccountAssetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountAssetAdapter implements AccountAssetPort {

	private final AccountAssetRepository accountAssetRepository;

	@Override
	public AccountAsset findAssetByKakaoId(String kakaoId) {
		AccountAssetEntity entity = accountAssetRepository.findByKakaoId(kakaoId);
		return toDomainModel(entity);
	}
	
	private AccountAsset toDomainModel(AccountAssetEntity entity) {
		return new AccountAsset(
				entity.getId(),
				entity.getTotalAsset(),
				entity.getDeposit(),
				entity.getTotalHoldingsValue(),
				entity.getTotalHoldingsQuantity(),
				entity.getInvestmentYield()
		);
	}
}
