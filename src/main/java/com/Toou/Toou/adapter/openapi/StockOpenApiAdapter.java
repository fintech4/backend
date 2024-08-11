package com.Toou.Toou.adapter.openapi;

import com.Toou.Toou.adapter.openapi.dto.StockDailyHistoryOpenApiDto;
import com.Toou.Toou.adapter.openapi.dto.StockOpenApiResponse;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.out.StockOpenApiPort;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
@RequiredArgsConstructor
public class StockOpenApiAdapter implements StockOpenApiPort {

	private final WebClient.Builder webClientBuilder;

	@Value("${open-api-getStockPrice.base-url}")
	private String baseUrl;

	@Value("${open-api-getStockPrice.service-key}")
	private String serviceKey;

	@Override
	public List<StockDailyHistory> findAllHistoriesBetweenDates(String companyName,
			LocalDate dateFrom, LocalDate dateTo) {

		// WebClient 생성
		WebClient webClient = webClientBuilder
				.baseUrl(baseUrl)
				.build();

		final List<StockDailyHistory> allHistories = new ArrayList<>();
		int pageNo = 1;
		int numOfRows = 100; // 한 페이지에 요청할 데이터 수

		try {
			while (true) {
				int currentPage = pageNo; // final로 처리된 변수 사용
				StockOpenApiResponse response = webClient.get()
						.uri(uriBuilder -> uriBuilder
								.queryParam("serviceKey", serviceKey)
								.queryParam("resultType", "json")
								.queryParam("numOfRows", numOfRows)
								.queryParam("pageNo", currentPage)
								.queryParam("beginBasDt", dateFrom.toString())
								.queryParam("itmsNm", companyName)
								.build())
						.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE) // JSON 응답 요청
						.retrieve()
						.bodyToMono(StockOpenApiResponse.class)
						.block(); // 동기적으로 데이터를 블로킹하여 반환

				if (response == null || response.getBody() == null) {
					break; // 응답이 null이면 루프를 종료
				}

				List<StockDailyHistoryOpenApiDto> items = response.getBody().getItems().getItem();
				allHistories.addAll(items.stream()
						.map(this::convertToDomain)
						.collect(Collectors.toList()));

				int totalCount = response.getBody().getTotalCount();
				int totalPages = (int) Math.ceil((double) totalCount / numOfRows);

				if (pageNo >= totalPages) {
					break; // 모든 페이지를 다 읽으면 루프를 종료
				}

				pageNo++; // 다음 페이지로 이동
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		return allHistories;
	}


	// DTO를 도메인 모델로 변환하는 메서드
	private StockDailyHistory convertToDomain(StockDailyHistoryOpenApiDto dto) {
		return new StockDailyHistory(
				null, // id는 DB에 저장할 때 자동 생성되므로 null로 설정
				dto.getSrtnCd(),
				dto.getItmsNm(),
				dto.getMrktCtg(),
				List.of(
						Long.parseLong(dto.getMkp()),
						Long.parseLong(dto.getHipr()),
						Long.parseLong(dto.getLopr()),
						Long.parseLong(dto.getClpr())
				),
				LocalDate.parse(dto.getBasDt(), DateTimeFormatter.BASIC_ISO_DATE)
		);
	}
}
