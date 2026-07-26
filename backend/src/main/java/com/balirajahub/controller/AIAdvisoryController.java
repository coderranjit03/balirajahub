package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.ai.AIAdviceResponse;
import com.balirajahub.dto.ai.GeminiResponse;
import com.balirajahub.service.AIAdvisoryService;
import com.balirajahub.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIAdvisoryController {

    private final AIAdvisoryService aiAdvisoryService;

    private final GeminiService geminiService;

    @GetMapping("/advice")
    public ApiResponse<AIAdviceResponse> generateAdvice() {

        return ApiResponse.success(
                "AI advice generated successfully.",
                aiAdvisoryService.generateAdvice()
        );
    }

    @GetMapping("/test")
    public ApiResponse<String> testGemini() {

        GeminiResponse response =
                geminiService.generateContent(
                        "Say hello in one sentence."
                );

        return ApiResponse.success(
                "Gemini connected successfully.",
                response.getContent()
        );
    }
}