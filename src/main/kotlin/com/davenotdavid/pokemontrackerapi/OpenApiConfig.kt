package com.davenotdavid.pokemontrackerapi

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun pokemonTrackerOpenApi(): OpenAPI =
        OpenAPI().info(
            Info()
                .title("Pokemon Tracker API")
                .description("Track which Pokemon you've captured: CRUD over a seeded Gen 1 Pokedex.")
                .version("v1")
        )
}
