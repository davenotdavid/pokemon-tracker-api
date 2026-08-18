package com.davenotdavid.pokemontrackerapi.pokemon

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class PokemonSeeder(private val pokemonRepository: PokemonRepository) : CommandLineRunner {

    override fun run(args: Array<String>) {
        if (pokemonRepository.count() == 0L) {
            pokemonRepository.saveAll(PokemonData.ALL)
        }
    }
}