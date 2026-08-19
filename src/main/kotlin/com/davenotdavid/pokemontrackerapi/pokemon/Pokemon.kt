package com.davenotdavid.pokemontrackerapi.pokemon

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

@Entity
data class Pokemon(
    @Id
    val id: Int,
    @field:NotBlank
    val name: String,
    @ElementCollection
    val type: List<String> = emptyList(),
    @field:Min(0)
    val hp: Int = 0,
    val isCaptured: Boolean = false,
)
