package com.balirajahub.service;

import com.balirajahub.dto.request.LoginRequest;
import com.balirajahub.dto.request.RegisterRequest;
import com.balirajahub.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}