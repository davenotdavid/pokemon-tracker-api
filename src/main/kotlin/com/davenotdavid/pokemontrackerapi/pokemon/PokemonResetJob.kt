package com.davenotdavid.pokemontrackerapi.pokemon

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Guards the public demo against tampering: anyone can capture/uncapture/delete Pokemon via the
 * live API, so the dataset is wiped and reseeded daily.
 */
@Component
class PokemonResetJob(private val pokemonRepository: PokemonRepository) {

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC") // daily at midnight UTC
    @Transactional
    fun reset() {
        pokemonRepository.deleteAll()
        pokemonRepository.saveAll(PokemonData.ALL)
    }
}