package com.balirajahub.service.impl;

import com.balirajahub.dto.request.RegisterRequest;
import com.balirajahub.dto.response.AuthResponse;
import com.balirajahub.entity.Role;
import com.balirajahub.entity.User;
import com.balirajahub.exception.EmailAlreadyExistsException;
import com.balirajahub.exception.PasswordMismatchException;
import com.balirajahub.exception.PhoneAlreadyExistsException;
import com.balirajahub.repository.UserRepository;
import com.balirajahub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {

        // 1. Validate password confirmation
        if (!java.util.Objects.equals(
                request.getPassword(),
                request.getConfirmPassword())) {

            throw new PasswordMismatchException("Passwords do not match.");
        }

        // 2. Check email uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered.");
        }

        // 3. Check phone uniqueness
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new PhoneAlreadyExistsException("Phone number is already registered.");
        }

        // 4. Create user
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .language(request.getLanguage())
                .role(Role.FARMER)
                .enabled(true)
                .emailVerified(false)
                .build();

        // 5. Save user
        User savedUser = userRepository.save(user);

        // 6. Return response
        return AuthResponse.builder()
                .message("User registered successfully.")
                .build();
    }
}