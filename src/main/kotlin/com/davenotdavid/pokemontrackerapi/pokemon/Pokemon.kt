package com.davenotdavid.pokemontrackerapi.pokemon

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Pokemon(
    @Id
    val id: Int,
    val name: String,
    @ElementCollection
    val type: List<String> = emptyList(),
    val hp: Int = 0,
    val isCaptured: Boolean = false,
)
