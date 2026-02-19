package com.example.usermanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/admin/test")
    public String admin() {
        return "Hello Admin";
    }

    @GetMapping("/seller/test")
    public String seller() {
        return "Hello Seller";
    }

    @GetMapping("/customer/test")
    public String customer() {
        return "Hello Customer";
    }
}
