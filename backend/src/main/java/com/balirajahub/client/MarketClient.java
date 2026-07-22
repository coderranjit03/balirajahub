package com.balirajahub.client;

import com.balirajahub.config.MarketApiConfig;
import com.balirajahub.dto.external.MarketApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class MarketClient {

    private static final Logger logger =
            LoggerFactory.getLogger(MarketClient.class);

    private final RestTemplate restTemplate;

    private final MarketApiConfig marketApiConfig;

    public MarketApiResponse getMarketPrices(
            String crop,
            String district) {

        UriComponentsBuilder builder = UriComponentsBuilder

                .fromHttpUrl(marketApiConfig.getUrl())

                .queryParam("api-key", marketApiConfig.getKey())

                .queryParam("format", "json")

                .queryParam("limit", 100);

        if (crop != null && !crop.isBlank()) {

            builder.queryParam("filters[commodity]", crop);
        }

        if (district != null && !district.isBlank()) {

            builder.queryParam("filters[district]", district);
        }

        logger.info("Calling Market API: {}", builder.toUriString());

        return restTemplate.getForObject(

                builder.toUriString(),

                MarketApiResponse.class
        );
    }
    }
