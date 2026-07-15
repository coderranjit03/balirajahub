package com.balirajahub.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String message;

    private String accessToken;

    private String refreshToken;

    private String tokenType;

}