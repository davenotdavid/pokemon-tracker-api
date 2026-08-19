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
class PokemonController(private val pokemonService: PokemonService) {

    @GetMapping
    fun getAll(): List<Pokemon> = pokemonService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Pokemon> {
        val pokemon = pokemonService.getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(pokemon)
    }

    @PostMapping
    fun create(@RequestBody pokemon: Pokemon): ResponseEntity<Pokemon> =
        ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.create(pokemon))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody pokemon: Pokemon): ResponseEntity<Pokemon> {
        val updated = pokemonService.update(id, pokemon) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        if (!pokemonService.delete(id)) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.noContent().build()
    }
}