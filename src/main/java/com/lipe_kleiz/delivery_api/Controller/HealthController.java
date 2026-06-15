package com.lipe_kleiz.delivery_api.Controller; //

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "API funcionando!";
    }
}
