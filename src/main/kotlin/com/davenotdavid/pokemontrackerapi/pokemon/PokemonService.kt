package com.davenotdavid.pokemontrackerapi.pokemon

import org.springframework.stereotype.Service

@Service
class PokemonService(private val pokemonRepository: PokemonRepository) {

    fun getAll(): List<Pokemon> = pokemonRepository.findAll()

    fun getById(id: Int): Pokemon =
        pokemonRepository.findById(id).orElseThrow { PokemonNotFoundException(id) }

    fun create(pokemon: Pokemon): Pokemon = pokemonRepository.save(pokemon)

    fun update(id: Int, pokemon: Pokemon): Pokemon {
        if (!pokemonRepository.existsById(id)) {
            throw PokemonNotFoundException(id)
        }
        return pokemonRepository.save(pokemon.copy(id = id))
    }

    fun delete(id: Int) {
        if (!pokemonRepository.existsById(id)) {
            throw PokemonNotFoundException(id)
        }
        pokemonRepository.deleteById(id)
    }
}