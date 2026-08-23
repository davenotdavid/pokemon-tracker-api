package com.davenotdavid.pokemontrackerapi.pokemon

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willThrow
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper

@WebMvcTest(PokemonController::class)
class PokemonControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockitoBean
    lateinit var pokemonService: PokemonService

    private fun pikachu() = Pokemon(id = 25, name = "Pikachu", type = listOf("Electric"), hp = 35, isCaptured = false)

    @Test
    fun `GET pokemon returns all pokemon`() {
        given(pokemonService.getAll()).willReturn(listOf(pikachu()))

        mockMvc.perform(get("/pokemon"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Pikachu"))
    }

    @Test
    fun `GET pokemon by id returns 200 when found`() {
        given(pokemonService.getById(25)).willReturn(pikachu())

        mockMvc.perform(get("/pokemon/25"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Pikachu"))
    }

    @Test
    fun `GET pokemon by id returns 404 when not found`() {
        given(pokemonService.getById(9999)).willThrow(PokemonNotFoundException(9999))

        mockMvc.perform(get("/pokemon/9999"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
    }

    @Test
    fun `POST pokemon creates and returns 201 for a valid body`() {
        val pokemon = pikachu()
        given(pokemonService.create(pokemon)).willReturn(pokemon)

        mockMvc.perform(
            post("/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemon))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("Pikachu"))
    }

    @Test
    fun `POST pokemon returns 400 for a blank name`() {
        val invalid = pikachu().copy(name = "")

        mockMvc.perform(
            post("/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `PUT pokemon updates and returns 200 when found`() {
        val updated = pikachu().copy(hp = 40)
        given(pokemonService.update(25, updated)).willReturn(updated)

        mockMvc.perform(
            put("/pokemon/25")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.hp").value(40))
    }

    @Test
    fun `DELETE pokemon returns 204 when found`() {
        mockMvc.perform(delete("/pokemon/25"))
            .andExpect(status().isNoContent)

        verify(pokemonService).delete(25)
    }

    @Test
    fun `DELETE pokemon returns 404 when not found`() {
        willThrow(PokemonNotFoundException(9999)).given(pokemonService).delete(9999)

        mockMvc.perform(delete("/pokemon/9999"))
            .andExpect(status().isNotFound)
    }
}