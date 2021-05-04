package com.callumezmoney.timefit.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class TestController {
    @GetMapping("/api/test/all")
    public void testAll(){}

    @GetMapping("/api/test/[role]")
    public void testRole(){}
}
