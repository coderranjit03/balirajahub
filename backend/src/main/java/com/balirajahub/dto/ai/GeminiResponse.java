package com.balirajahub.dto.ai;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiResponse {

    private String content;

    private Integer promptTokens;

    private Integer completionTokens;

    private Integer totalTokens;

}
