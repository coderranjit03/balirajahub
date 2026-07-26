package com.balirajahub.dto.ai.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageMetadata {

    @JsonProperty("promptTokenCount")
    private Integer promptTokenCount;

    @JsonProperty("candidatesTokenCount")
    private Integer candidatesTokenCount;

    @JsonProperty("totalTokenCount")
    private Integer totalTokenCount;

}