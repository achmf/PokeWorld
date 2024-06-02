package com.example.pokedex.data.retrofit

import retrofit2.Call

class PokemonRepository {
    private val pokeApiService: PokeApiService by lazy {
        RetrofitClient.instance.create(PokeApiService::class.java)
    }

    fun getPokemon(id: Int): Call<Pokemon> {
        return pokeApiService.getPokemon(id)
    }
}
