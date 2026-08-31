package com.davenotdavid.pokemontrackerapi.pokemon

import org.springframework.data.jpa.repository.JpaRepository

interface PokemonRepository : JpaRepository<Pokemon, Int> {
    fun findAllByOrderByIdAsc(): List<Pokemon>
}