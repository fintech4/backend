package com.Toou.Toou.port.in;

import com.Toou.Toou.port.in.dto.VoidResponse;
import com.Toou.Toou.usecase.AccountAssetUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AccountAssetUseCase accountAssetUseCase;

	@GetMapping("/login/kakao")
	@Operation(summary = "카카오 로그인 데모", description = "providerId로 더미 데이터인 kakao123, kakao456 를 입력하시면 돼요 ")
//TODO: OAuth 완료 후 수정
	public ResponseEntity<VoidResponse> loginWithKakao(@RequestParam String providerId,
			HttpServletResponse response) {
		AccountAssetUseCase.Input input = new AccountAssetUseCase.Input(providerId);
		AccountAssetUseCase.Output output = accountAssetUseCase.execute(input);
		Cookie cookie = new Cookie("providerId", providerId);
		cookie.setHttpOnly(true); // 보안 설정
		cookie.setPath("/");
		cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
		response.addCookie(cookie);
		return ResponseEntity.ok().body(VoidResponse.ok());
	}
}
