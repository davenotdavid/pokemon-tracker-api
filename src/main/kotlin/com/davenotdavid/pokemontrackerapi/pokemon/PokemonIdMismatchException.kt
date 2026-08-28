package com.davenotdavid.pokemontrackerapi.pokemon

class PokemonIdMismatchException(pathId: Int, bodyId: Int) :
    RuntimeException("Path id $pathId does not match body id $bodyId")
