package com.balirajahub.client;

import com.balirajahub.config.GeminiConfig;
import com.balirajahub.dto.ai.GeminiResponse;
import com.balirajahub.dto.ai.openrouter.OpenRouterRequest;
import com.balirajahub.dto.ai.openrouter.OpenRouterResponse;
import com.balirajahub.exception.GeminiApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeminiClientImpl implements GeminiClient {

    private final RestClient restClient;

    private final GeminiConfig geminiConfig;



    @Override
    public GeminiResponse generateContent(String prompt) {

        OpenRouterRequest request = OpenRouterRequest.builder()

                .model(geminiConfig.getModel())

                .messages(
                        List.of(
                                OpenRouterRequest.Message.builder()
                                        .role("user")
                                        .content(prompt)
                                        .build()
                        )
                )

                .build();

        try {

            OpenRouterResponse response = restClient.post()

                    .uri(geminiConfig.getBaseUrl())

                    .header(HttpHeaders.AUTHORIZATION,
                            "Bearer " + geminiConfig.getApiKey())

                    .header("HTTP-Referer",
                            "https://balirajahub.local")

                    .header("X-Title",
                            "BalirajaHub")

                    .contentType(MediaType.APPLICATION_JSON)

                    .body(request)

                    .retrieve()

                    .body(OpenRouterResponse.class);
            if (response == null
                    || response.getChoices() == null
                    || response.getChoices().isEmpty()) {

                throw new GeminiApiException(
                        "No response received from AI provider.");
            }

            String content = response.getChoices()
                    .getFirst()
                    .getMessage()
                    .getContent();

            return GeminiResponse.builder()

                    .content(content)

                    .promptTokens(0)

                    .completionTokens(0)

                    .totalTokens(0)

                    .build();

        } catch (RestClientResponseException ex) {

            throw new GeminiApiException(
                    ex.getResponseBodyAsString(),
                    ex);
        }
    }
}
