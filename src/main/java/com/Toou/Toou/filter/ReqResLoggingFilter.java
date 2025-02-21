package com.Toou.Toou.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class ReqResLoggingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		if (shouldNotFilter(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

		long startTime = System.currentTimeMillis();
		filterChain.doFilter(requestWrapper, responseWrapper);
		long elapsedTime = System.currentTimeMillis() - startTime;

		try {
			log.info(
					"🌐 [HTTP REQUEST] IP: {}, Method: {}, URI: {}, Query Params: {}, Body: {} | 📩 [HTTP RESPONSE] Status: {}, Time: {}ms, Body: {}",
					getClientIp(request), request.getMethod(), request.getRequestURI(),
					request.getQueryString() != null ? request.getQueryString() : "None",
					getRequestBody(requestWrapper), response.getStatus(), elapsedTime,
					getResponseBody(responseWrapper));

			responseWrapper.copyBodyToResponse(); // 클라이언트에게 정상적으로 응답 전달
		} catch (Exception e) {
			log.error("[{}] Logging 실패", this.getClass().getSimpleName(), e);
		}
	}

	// 정적 리소스 및 불필요한 요청을 로깅에서 제외
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/static/") || path.endsWith(".css") || path.endsWith(".js");
	}

	/**
	 * 요청자의 IP 주소 가져오기 (X-Forwarded-For 우선)
	 */
	private String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		return (ip == null || ip.isBlank()) ? request.getRemoteAddr() : ip.split(",")[0].trim();
	}

	// 요청 바디를 읽고 로깅
	private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
		return extractBody(requestWrapper.getContentAsByteArray());
	}

	// 응답 바디를 읽고 로깅
	private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
		return extractBody(responseWrapper.getContentAsByteArray());
	}

	// body 추출. 너무 길면 BODY_MAX_LENGTH 자로 제한
	private String extractBody(byte[] contentAsByteArray) {
		if (contentAsByteArray.length == 0) {
			return "Empty";
		}

		String body = new String(contentAsByteArray, StandardCharsets.UTF_8);
		int BODY_MAX_LENGTH = 500;
		return body.length() > BODY_MAX_LENGTH ? body.substring(0, BODY_MAX_LENGTH) + "..." : body;
	}

}
