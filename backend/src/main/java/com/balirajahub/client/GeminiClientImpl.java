package com.balirajahub.client;

import com.balirajahub.config.GeminiConfig;
import com.balirajahub.dto.ai.GeminiResponse;
import com.balirajahub.dto.ai.gemini.Content;
import com.balirajahub.dto.ai.gemini.GeminiApiResponse;
import com.balirajahub.dto.ai.gemini.GeminiRequest;
import com.balirajahub.dto.ai.gemini.Part;
import com.balirajahub.dto.ai.gemini.UsageMetadata;
import com.balirajahub.exception.GeminiApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeminiClientImpl implements GeminiClient {

    private final RestClient restClient;

    private final GeminiConfig geminiConfig;

    @Override
    public GeminiResponse generateContent(String prompt) {

        GeminiRequest request = GeminiRequest.builder()
                .contents(
                        List.of(
                                Content.builder()
                                        .parts(
                                                List.of(
                                                        Part.builder()
                                                                .text(prompt)
                                                                .build()
                                                )
                                        )
                                        .build()
                        )
                )
                .build();


        String url = geminiConfig.getBaseUrl()
                + "/"
                + geminiConfig.getModel()
                + ":generateContent"
                + "?key="
                + geminiConfig.getApiKey();

        String key = geminiConfig.getApiKey();

        System.out.println("=================================");
        System.out.println("Using API Key : "
                + key.substring(0, 8)
                + "..."
                + key.substring(key.length() - 4));
        System.out.println("Model         : " + geminiConfig.getModel());
        System.out.println("Base URL      : " + geminiConfig.getBaseUrl());
        System.out.println("=================================");
        try {

            System.out.println("=================================");
            System.out.println("API Key Loaded : " + (geminiConfig.getApiKey() != null));
            System.out.println("Model          : " + geminiConfig.getModel());
            System.out.println("Base URL       : " + geminiConfig.getBaseUrl());
            System.out.println("=================================");
            GeminiApiResponse response = restClient.post()

                    .uri(url)

                    .body(request)

                    .retrieve()

                    .body(GeminiApiResponse.class);

            if (response == null
                    || response.getCandidates() == null
                    || response.getCandidates().isEmpty()) {

                throw new GeminiApiException(
                        "No response received from Gemini API.");
            }

            UsageMetadata usage = response.getUsageMetadata();

            return GeminiResponse.builder()

                    .content(
                            response.getCandidates()
                                    .getFirst()
                                    .getContent()
                                    .getParts()
                                    .getFirst()
                                    .getText())

                    .promptTokens(
                            usage != null
                                    ? usage.getPromptTokenCount()
                                    : 0)

                    .completionTokens(
                            usage != null
                                    ? usage.getCandidatesTokenCount()
                                    : 0)

                    .totalTokens(
                            usage != null
                                    ? usage.getTotalTokenCount()
                                    : 0)

                    .build();

        } catch (RestClientResponseException ex) {

            System.out.println("================================");
            System.out.println("Status Code : " + ex.getStatusCode());
            System.out.println("Response Body:");
            System.out.println(ex.getResponseBodyAsString());
            System.out.println("================================");

            throw new GeminiApiException(
                    ex.getResponseBodyAsString(),
                    ex);

        } catch (RestClientException ex) {

            ex.printStackTrace();

            throw new GeminiApiException(
                    ex.getMessage(),
                    ex);
        }
    }
}