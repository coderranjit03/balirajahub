package com.balirajahub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FarmerTestController {

    @GetMapping("/api/farmer/test")
    public String farmer() {
        return "Welcome Farmer";
    }
}