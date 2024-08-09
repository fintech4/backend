package com.Toou.Toou.port.in;

import com.Toou.Toou.port.in.dto.VoidResponse;
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

	@GetMapping("/login/kakao")
	@Operation(summary = "카카오 로그인 데모", description = "kakaoId로 더미 데이터인 kakao123, kakao456 를 입력하시면 되요 ")
//TODO: OAuth 완료 후 수정
	public ResponseEntity<VoidResponse> loginWithKakao(@RequestParam String kakaoId,
			HttpServletResponse response) {
		// 예시: 여기서는 이미 Kakao 인증이 완료되었다고 가정하고, kakaoId를 쿠키에 저장
		Cookie cookie = new Cookie("kakaoId", kakaoId);
		cookie.setHttpOnly(true); // 보안 설정
		cookie.setPath("/");
		cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
		response.addCookie(cookie);
		return ResponseEntity.ok().body(VoidResponse.ok());
	}
}
