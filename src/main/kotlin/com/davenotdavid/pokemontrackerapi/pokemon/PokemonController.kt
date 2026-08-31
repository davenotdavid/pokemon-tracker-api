package com.davenotdavid.pokemontrackerapi.pokemon

import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = "List all Pokemon")
    @GetMapping
    fun getAll(): List<Pokemon> = pokemonService.getAll()

    @Operation(summary = "Get a single Pokemon by its Pokedex id")
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): Pokemon = pokemonService.getById(id)

    @Operation(summary = "Add a new Pokemon")
    @PostMapping
    fun create(@Valid @RequestBody pokemon: Pokemon): ResponseEntity<Pokemon> =
        ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.create(pokemon))

    @Operation(summary = "Update an existing Pokemon (e.g. mark it as captured)")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody pokemon: Pokemon): Pokemon =
        pokemonService.update(id, pokemon)

    @Operation(summary = "Remove a Pokemon")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        pokemonService.delete(id)
        return ResponseEntity.noContent().build()
    }
}