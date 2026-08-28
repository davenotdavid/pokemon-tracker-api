package com.davenotdavid.pokemontrackerapi.pokemon

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class)
class PokemonServiceTest {

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    private lateinit var pokemonService: PokemonService

    @BeforeEach
    fun setUp() {
        pokemonService = PokemonService(pokemonRepository)
    }

    private fun pikachu() = Pokemon(id = 25, name = "Pikachu", type = listOf("Electric"), hp = 35, isCaptured = false)

    @Test
    fun `getAll returns all pokemon from the repository`() {
        given(pokemonRepository.findAll()).willReturn(listOf(pikachu()))

        assertEquals(listOf(pikachu()), pokemonService.getAll())
    }

    @Test
    fun `getById returns the pokemon when it exists`() {
        given(pokemonRepository.findById(25)).willReturn(Optional.of(pikachu()))

        assertEquals(pikachu(), pokemonService.getById(25))
    }

    @Test
    fun `getById throws PokemonNotFoundException when it does not exist`() {
        given(pokemonRepository.findById(9999)).willReturn(Optional.empty())

        assertFailsWith<PokemonNotFoundException> { pokemonService.getById(9999) }
    }

    @Test
    fun `create saves and returns the pokemon`() {
        val pokemon = pikachu()
        given(pokemonRepository.existsById(25)).willReturn(false)
        given(pokemonRepository.save(pokemon)).willReturn(pokemon)

        assertEquals(pokemon, pokemonService.create(pokemon))
    }

    @Test
    fun `create throws PokemonAlreadyExistsException when the id is taken`() {
        given(pokemonRepository.existsById(25)).willReturn(true)

        assertFailsWith<PokemonAlreadyExistsException> { pokemonService.create(pikachu()) }

        verify(pokemonRepository, never()).save(pikachu())
    }

    @Test
    fun `update saves when the pokemon exists`() {
        val updated = pikachu().copy(hp = 40)
        given(pokemonRepository.existsById(25)).willReturn(true)
        given(pokemonRepository.save(updated)).willReturn(updated)

        assertEquals(40, pokemonService.update(25, updated).hp)
    }

    @Test
    fun `update throws PokemonNotFoundException when the pokemon does not exist`() {
        given(pokemonRepository.existsById(9999)).willReturn(false)

        assertFailsWith<PokemonNotFoundException> { pokemonService.update(9999, pikachu().copy(id = 9999)) }
    }

    @Test
    fun `update throws PokemonIdMismatchException when body id does not match path id`() {
        assertFailsWith<PokemonIdMismatchException> { pokemonService.update(25, pikachu().copy(id = 26)) }

        verify(pokemonRepository, never()).save(pikachu())
    }

    @Test
    fun `delete removes the pokemon when it exists`() {
        given(pokemonRepository.existsById(25)).willReturn(true)

        pokemonService.delete(25)

        verify(pokemonRepository).deleteById(25)
    }

    @Test
    fun `delete throws PokemonNotFoundException when the pokemon does not exist`() {
        given(pokemonRepository.existsById(9999)).willReturn(false)

        assertFailsWith<PokemonNotFoundException> { pokemonService.delete(9999) }

        verify(pokemonRepository, never()).deleteById(9999)
    }
}