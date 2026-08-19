package com.davenotdavid.pokemontrackerapi.pokemon

import org.springframework.stereotype.Service

@Service
class PokemonService(private val pokemonRepository: PokemonRepository) {

    fun getAll(): List<Pokemon> = pokemonRepository.findAll()

    fun getById(id: Int): Pokemon? = pokemonRepository.findById(id).orElse(null)

    fun create(pokemon: Pokemon): Pokemon = pokemonRepository.save(pokemon)

    fun update(id: Int, pokemon: Pokemon): Pokemon? {
        if (!pokemonRepository.existsById(id)) {
            return null
        }
        return pokemonRepository.save(pokemon.copy(id = id))
    }

    fun delete(id: Int): Boolean {
        if (!pokemonRepository.existsById(id)) {
            return false
        }
        pokemonRepository.deleteById(id)
        return true
    }
}