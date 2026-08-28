package com.davenotdavid.pokemontrackerapi.pokemon

import org.springframework.stereotype.Service

@Service
class PokemonService(private val pokemonRepository: PokemonRepository) {

    fun getAll(): List<Pokemon> = pokemonRepository.findAll()

    fun getById(id: Int): Pokemon =
        pokemonRepository.findById(id).orElseThrow { PokemonNotFoundException(id) }

    fun create(pokemon: Pokemon): Pokemon {
        if (pokemonRepository.existsById(pokemon.id)) {
            throw PokemonAlreadyExistsException(pokemon.id)
        }
        return pokemonRepository.save(pokemon)
    }

    fun update(id: Int, pokemon: Pokemon): Pokemon {
        if (pokemon.id != id) {
            throw PokemonIdMismatchException(id, pokemon.id)
        }
        if (!pokemonRepository.existsById(id)) {
            throw PokemonNotFoundException(id)
        }
        return pokemonRepository.save(pokemon)
    }

    fun delete(id: Int) {
        if (!pokemonRepository.existsById(id)) {
            throw PokemonNotFoundException(id)
        }
        pokemonRepository.deleteById(id)
    }
}