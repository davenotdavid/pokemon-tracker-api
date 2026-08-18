package com.davenotdavid.pokemontrackerapi.pokemon

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Pokemon(
    @Id
    val id: Int,
    val name: String,
)