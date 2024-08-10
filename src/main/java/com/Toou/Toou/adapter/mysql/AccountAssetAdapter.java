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
	public AccountAsset findAssetByKakaoId(String kakaoId) {
		AccountAssetEntity entity = accountAssetJpaRepository.findByKakaoId(kakaoId)
				.orElseThrow(() -> new CustomException(CustomExceptionDetail.USER_NOT_FOUND));
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
