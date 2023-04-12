package com.rgnrk.rgnrk_ti;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Poker Planning API",
        description = "REST API to support a poker planning session",
        version = "0.0.1"
))
public class RgnrkTiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RgnrkTiApplication.class, args);
    }

}
