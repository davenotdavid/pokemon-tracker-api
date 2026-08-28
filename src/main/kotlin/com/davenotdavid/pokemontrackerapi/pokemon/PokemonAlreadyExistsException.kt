package com.davenotdavid.pokemontrackerapi.pokemon

class PokemonAlreadyExistsException(id: Int) : RuntimeException("Pokemon already exists with id $id")
