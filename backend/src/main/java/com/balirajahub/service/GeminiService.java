package com.balirajahub.service;

import com.balirajahub.dto.ai.GeminiResponse;

public interface GeminiService {

    GeminiResponse generateContent(String prompt);

}