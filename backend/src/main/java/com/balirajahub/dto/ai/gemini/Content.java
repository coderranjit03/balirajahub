package com.balirajahub.dto.ai.gemini;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {

    private List<Part> parts;

}