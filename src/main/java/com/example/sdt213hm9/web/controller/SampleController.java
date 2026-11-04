package com.example.sdt213hm9.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Section 5 of the tutorial: a basic controller.
 */
@Controller
public class SampleController {

    @GetMapping("/sample")
    public String showForm() {
        return "sample";
    }

}
