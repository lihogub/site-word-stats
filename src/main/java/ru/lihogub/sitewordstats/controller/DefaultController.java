package ru.lihogub.sitewordstats.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class DefaultController {
    @GetMapping
    public String indexEndpoint() {
        log.debug("indexEndpoint called");
        return "Hello, World!";
    }
}
