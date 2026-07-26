package com.balirajahub.service.impl;

import com.balirajahub.client.GeminiClient;
import com.balirajahub.dto.ai.GeminiResponse;
import com.balirajahub.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl
        implements GeminiService {

    private final GeminiClient geminiClient;

    @Override
    public GeminiResponse generateContent(
            String prompt) {

        return geminiClient.generateContent(prompt);

    }
}