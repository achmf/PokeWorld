package com.example.pokedex.data.retrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel : ViewModel() {
    private val repository = PokemonRepository()
    val pokemon = MutableLiveData<List<Pokemon>>()
    val error = MutableLiveData<String>()

    init {
        fetchAllPokemons()
    }

    fun fetchAllPokemons() {
        val fetchedPokemonList = mutableListOf<Pokemon>()
        val fetchCalls = (1..50).map { id -> repository.getPokemon(id) }

        var pendingCalls = fetchCalls.size // Track pending calls

        fetchCalls.forEach { call ->
            call.enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.isSuccessful) {
                        response.body()?.let { pokemonResponse ->
                            fetchedPokemonList.add(pokemonResponse)
                        }
                    } else {
                        error.postValue(response.message())
                    }

                    // Check if all calls are completed
                    if (fetchedPokemonList.size == pendingCalls) {
                        val sortedList = fetchedPokemonList.sortedBy { pokemon -> pokemon.id }
                        pokemon.postValue(sortedList)
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    error.postValue(t.message)

                    // Decrement pending calls on failure as well
                    if (--pendingCalls == 0) {
                        // If all calls are completed (even if some failed), update UI
                        val sortedList = fetchedPokemonList.sortedBy { pokemon -> pokemon.id }
                        pokemon.postValue(sortedList)
                    }
                }
            })
        }
    }
}

