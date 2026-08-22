package com.davenotdavid.pokemontrackerapi.pokemon

import jakarta.validation.Valid
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
class PokemonController(private val pokemonService: PokemonService) {

    @GetMapping
    fun getAll(): List<Pokemon> = pokemonService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): Pokemon = pokemonService.getById(id)

    @PostMapping
    fun create(@Valid @RequestBody pokemon: Pokemon): ResponseEntity<Pokemon> =
        ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.create(pokemon))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody pokemon: Pokemon): Pokemon =
        pokemonService.update(id, pokemon)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        pokemonService.delete(id)
        return ResponseEntity.noContent().build()
    }
}