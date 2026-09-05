package com.davenotdavid.pokemontrackerapi.pokemon

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PokemonResetJobTest {

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    @Test
    fun `reset deletes all pokemon then reseeds from PokemonData`() {
        PokemonResetJob(pokemonRepository).reset()

        verify(pokemonRepository).deleteAll()
        verify(pokemonRepository).saveAll(PokemonData.ALL)
    }
}