package com.taghavi.imdbsearchapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "IMDB Search API", version = "1.0", description = "API for searching IMDB data"))
public class ImdbSearchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdbSearchApiApplication.class, args);
    }

}
