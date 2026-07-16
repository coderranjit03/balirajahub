package com.balirajahub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {

    @GetMapping("/api/admin/test")
    public String admin() {
        return "Welcome Admin";
    }
}