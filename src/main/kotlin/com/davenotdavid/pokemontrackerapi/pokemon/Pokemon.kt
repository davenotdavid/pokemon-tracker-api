package com.davenotdavid.pokemontrackerapi.pokemon

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

@Entity
data class Pokemon(
    @Id
    @field:Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Pokedex number; must be explicit on create")
    val id: Int,
    @field:NotBlank
    val name: String,
    @ElementCollection
    val type: List<String> = emptyList(),
    @field:Min(0)
    val hp: Int = 0,
    @get:JsonProperty("isCaptured")
    val isCaptured: Boolean = false,
)
