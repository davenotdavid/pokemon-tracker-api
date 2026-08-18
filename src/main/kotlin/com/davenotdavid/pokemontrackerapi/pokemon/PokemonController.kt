package com.davenotdavid.pokemontrackerapi.pokemon

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController(private val pokemonRepository: PokemonRepository) {

    @GetMapping
    fun getAll(): List<Pokemon> = pokemonRepository.findAll()
}