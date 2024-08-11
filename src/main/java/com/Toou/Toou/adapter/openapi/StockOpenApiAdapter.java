package com.Toou.Toou.adapter.openapi;

import com.Toou.Toou.adapter.openapi.dto.StockDailyHistoryOpenApiDto;
import com.Toou.Toou.adapter.openapi.dto.StockOpenApiResponse;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.out.StockOpenApiPort;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
	public List<StockDailyHistory> findAllHistoriesBetweenDates(Long stockMetadataId,
			String companyName,
			LocalDate dateFrom, LocalDate dateTo) {

		WebClient webClient = webClientBuilder
				.baseUrl(baseUrl)
				.build();

		final List<StockDailyHistory> allHistories = new ArrayList<>();
		int pageNo = 1;
		int numOfRows = 100;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedFromDate = dateFrom.format(formatter);
		String formattedToDate = dateTo.format(formatter);

		try {
			while (true) {
				URI uri = new URI(baseUrl + "?serviceKey=" + serviceKey
						+ "&resultType=json"
						+ "&numOfRows=" + numOfRows
						+ "&pageNo=" + pageNo
						+ "&beginBasDt=" + formattedFromDate
						+ "&endBasDt=" + formattedToDate
						+ "&itmsNm=" + URLEncoder.encode(companyName, StandardCharsets.UTF_8.toString()));

				StockOpenApiResponse response = webClient.get()
						.uri(uri)
						.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
						.retrieve()
						.bodyToMono(StockOpenApiResponse.class)
						.block();

				if (response == null || response.getResponse() == null
						|| response.getResponse().getBody() == null) {
					break;
				}

				List<StockDailyHistoryOpenApiDto> items = response.getResponse().getBody().getItems()
						.getItem();
				if (items == null || items.isEmpty()) {
					break;
				}

				List<StockDailyHistory> newHistories = items.stream()
						.map(dto -> convertToDomain(dto, stockMetadataId))
						.collect(Collectors.toList());

				allHistories.addAll(newHistories);

				int totalCount = response.getResponse().getBody().getTotalCount();
				int totalPages = (int) Math.ceil((double) totalCount / numOfRows);

				if (pageNo >= totalPages) {
					break;
				}

				pageNo++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		return allHistories;
	}

	private StockDailyHistory convertToDomain(StockDailyHistoryOpenApiDto dto, Long stockMetadataId) {
		return new StockDailyHistory(
				null, // id는 DB에 저장할 때 자동 생성되므로 null로 설정
				stockMetadataId, // stockMetadataId를 외부에서 전달받아 사용
				Long.parseLong(dto.getMkp()), // openPrice
				Long.parseLong(dto.getHipr()), // highPrice
				Long.parseLong(dto.getLopr()), // lowPrice
				Long.parseLong(dto.getClpr()), // closingPrice
				LocalDate.parse(dto.getBasDt(), DateTimeFormatter.BASIC_ISO_DATE) // date
		);
	}
}
