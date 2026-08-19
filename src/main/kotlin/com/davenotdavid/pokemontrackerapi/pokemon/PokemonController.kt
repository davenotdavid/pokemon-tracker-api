package com.davenotdavid.pokemontrackerapi.pokemon

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController(private val pokemonRepository: PokemonRepository) {

    @GetMapping
    fun getAll(): List<Pokemon> = pokemonRepository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Pokemon> {
        val pokemon = pokemonRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(pokemon)
    }

    @PostMapping
    fun create(@RequestBody pokemon: Pokemon): ResponseEntity<Pokemon> =
        ResponseEntity.status(HttpStatus.CREATED).body(pokemonRepository.save(pokemon))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody pokemon: Pokemon): ResponseEntity<Pokemon> {
        if (!pokemonRepository.existsById(id)) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(pokemonRepository.save(pokemon.copy(id = id)))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        if (!pokemonRepository.existsById(id)) {
            return ResponseEntity.notFound().build()
        }
        pokemonRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}