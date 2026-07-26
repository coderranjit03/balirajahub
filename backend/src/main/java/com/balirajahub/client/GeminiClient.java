package com.balirajahub.client;

import com.balirajahub.dto.ai.GeminiResponse;

public interface GeminiClient {

    GeminiResponse generateContent(String prompt);

}