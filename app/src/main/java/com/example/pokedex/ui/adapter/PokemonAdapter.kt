package com.example.pokedex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.data.retrofit.Pokemon
import com.example.pokedex.databinding.ItemPokemonBinding
import kotlin.math.absoluteValue

class PokemonAdapter(
    private val pokemonList: List<Pokemon>,
    private val itemClickListener: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private lateinit var context: Context
    private val colors = intArrayOf(
        R.color.color1, R.color.color2, R.color.color3, R.color.color4,
        R.color.color5, R.color.color6, R.color.color7, R.color.color8,
        R.color.color9, R.color.color10
    )

    // Initialize filteredPokemonList with all Pokémon in the constructor
    private var filteredPokemonList = mutableListOf<Pokemon>().apply {
        addAll(pokemonList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        context = parent.context
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = filteredPokemonList[position]
        holder.bind(pokemon)
    }

    override fun getItemCount() = filteredPokemonList.size

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.apply {
                pokemonName.text = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                }
                Glide.with(itemView.context)
                    .load(pokemon.sprites.front_default)
                    .into(pokemonImage)

                val colorIndex = pokemon.name.hashCode().absoluteValue % colors.size
                val color = colors[colorIndex]
                cardBackground.setBackgroundColor(ContextCompat.getColor(context, color))

                itemView.setOnClickListener { itemClickListener(pokemon) }
            }
        }
    }

    // Method to filter Pokémon based on their type
    fun filterByType(type: String) {
        filteredPokemonList = if (type.isEmpty() || type == "Unfilter") {
            pokemonList.toMutableList()
        } else {
            pokemonList.filter { pokemon ->
                pokemon.types.any { it.type.name.equals(type, ignoreCase = true) }
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}