package com.balirajahub.dto.ai.openrouter;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenRouterRequest {

    private String model;

    private List<Message> messages;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Message {

        private String role;

        private String content;
    }
}
