package com.davenotdavid.pokemontrackerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PokemonTrackerApiApplication

fun main(args: Array<String>) {
    runApplication<PokemonTrackerApiApplication>(*args)
}
