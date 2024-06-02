package com.example.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.data.retrofit.Pokemon
import com.example.pokedex.data.retrofit.PokemonViewModel
import com.example.pokedex.databinding.ActivityPokemonBinding
import com.example.pokedex.ui.adapter.PokemonAdapter

class PokemonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var pokemonAdapter: PokemonAdapter
    private val pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]

        setupRecyclerView()
        setupFilterButton()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

        viewModel.pokemon.observe(this, Observer { pokemons ->
            pokemonList.clear()
            pokemonList.addAll(pokemons)
            pokemonAdapter.notifyDataSetChanged()

            // Set default filter after fetching Pokémon data
            pokemonAdapter.filterByType("Unfilter")
        })

        viewModel.error.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })

        fetchAllPokemons()
    }

    private fun setupRecyclerView() {
        pokemonAdapter = PokemonAdapter(pokemonList) { pokemon ->
            navigateToDetail(pokemon)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PokemonActivity)
            adapter = pokemonAdapter
        }
    }

    private fun setupFilterButton() {
        binding.filterButton.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun showFilterDialog() {
        val types = mutableListOf<String>()
        types.add("Unfilter")

        // Extract all unique Pokémon types
        pokemonList.forEach { pokemon ->
            pokemon.types.forEach { type ->
                if (!types.contains(type.type.name)) {
                    types.add(type.type.name)
                }
            }
        }

        // Capitalize the first letter of each type
        val capitalizedTypes = types.map { type ->
            type.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }

        // Show dialog with capitalized type options
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Filter by Type")
        dialogBuilder.setItems(capitalizedTypes.toTypedArray()) { dialog, which ->
            val selectedType = types[which]
            pokemonAdapter.filterByType(selectedType)
            dialog.dismiss()
        }
        dialogBuilder.show()
    }

    private fun navigateToDetail(pokemon: Pokemon) {
        val intent = Intent(this, DetailPokemonActivity::class.java)
        intent.putExtra("pokemon", pokemon)
        startActivity(intent)
    }

    private fun fetchAllPokemons() {
        viewModel.fetchAllPokemons()
    }
}
