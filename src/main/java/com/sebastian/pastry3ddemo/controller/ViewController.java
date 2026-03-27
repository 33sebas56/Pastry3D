package com.sebastian.pastry3ddemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/viewer")
    public String viewerPage() {
        return "viewer";
    }
}