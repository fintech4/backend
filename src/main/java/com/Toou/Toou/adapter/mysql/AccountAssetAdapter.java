package com.Toou.Toou.adapter.mysql;


import com.Toou.Toou.adapter.mysql.entity.AccountAssetEntity;
import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
import com.Toou.Toou.port.out.AccountAssetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountAssetAdapter implements AccountAssetPort {

	private final AccountAssetJpaRepository accountAssetJpaRepository;

	@Override
	public AccountAsset findByProviderId(String providerId) {
		AccountAssetEntity entity = accountAssetJpaRepository.findByProviderId(providerId)
				.orElseThrow(() -> new CustomException(CustomExceptionDetail.USER_NOT_FOUND));
		return toDomainModel(entity);
	}

	@Override
	public AccountAsset saveAsset(AccountAsset accountAsset) {
		AccountAssetEntity entity = toEntity(accountAsset);
		AccountAssetEntity savedEntity = accountAssetJpaRepository.save(entity);
		return toDomainModel(savedEntity);
	}

	private AccountAsset toDomainModel(AccountAssetEntity entity) {
		return new AccountAsset(
				entity.getId(),
				entity.getOauthProvider(),
				entity.getProviderId(),
				entity.getTotalAsset(),
				entity.getDeposit(),
				entity.getTotalHoldingsValue(),
				entity.getTotalHoldingsQuantity(),
				entity.getInvestmentYield(),
				entity.getTotalPrincipal()
		);
	}

	private AccountAssetEntity toEntity(AccountAsset accountAsset) {
		return new AccountAssetEntity(
				accountAsset.getId(),
				accountAsset.getOauthProvider(),
				accountAsset.getProviderId(),
				accountAsset.getTotalAsset(),
				accountAsset.getDeposit(),
				accountAsset.getTotalHoldingsValue(),
				accountAsset.getTotalHoldingsQuantity(),
				accountAsset.getInvestmentYield(),
				accountAsset.getTotalPrincipal()
		);
	}
}
