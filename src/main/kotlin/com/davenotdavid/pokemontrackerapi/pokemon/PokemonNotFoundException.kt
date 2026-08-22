package com.davenotdavid.pokemontrackerapi.pokemon

class PokemonNotFoundException(id: Int) : RuntimeException("Pokemon not found with id $id")