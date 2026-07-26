package com.balirajahub.service;

import com.balirajahub.dto.ai.AIContext;

public interface PromptBuilderService {

    String buildPrompt(AIContext context);

}