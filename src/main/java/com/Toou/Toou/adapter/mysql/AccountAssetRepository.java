package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.AccountAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAssetRepository extends JpaRepository<AccountAssetEntity, Long> {

	AccountAssetEntity findByKakaoId(String kakaoId);
}