package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.AccountAssetEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAssetJpaRepository extends JpaRepository<AccountAssetEntity, Long> {

	Optional<AccountAssetEntity> findByKakaoId(String kakaoId);

}