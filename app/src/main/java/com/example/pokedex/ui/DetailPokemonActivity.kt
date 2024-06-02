package com.example.pokedex.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.data.retrofit.Pokemon
import com.example.pokedex.databinding.ActivityDetailPokemonBinding
import java.util.Locale

class DetailPokemonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPokemonBinding
    private val pokemonBackgroundMap = HashMap<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")
        if (pokemon != null) {
            val backgroundColor = pokemonBackgroundMap[pokemon.id] ?: setPokemonBackgroundColor(pokemon.id)
            binding.main.setBackgroundColor(backgroundColor)
            populatePokemonDetails(pokemon)
        }

        // Set the back button click listener
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setPokemonBackgroundColor(pokemonId: Int): Int {
        val colorResources = arrayOf(
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10
        )

        // Use a fixed algorithm to determine the background color based on Pokemon ID
        val colorIndex = pokemonId % colorResources.size
        val color = ContextCompat.getColor(this, colorResources[colorIndex])
        pokemonBackgroundMap[pokemonId] = color
        return color
    }

    private fun populatePokemonDetails(pokemon: Pokemon) {
        binding.apply {
            pokemonName.text = pokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }

            Glide.with(this@DetailPokemonActivity)
                .load(pokemon.sprites.front_default)
                .placeholder(R.drawable.placeholder_image)
                .into(pokemonImage)

            pokemonHeight.text = getString(R.string.pokemon_height, pokemon.height)
            pokemonWeight.text = getString(R.string.pokemon_weight, pokemon.weight)

            pokemonAbilities.text = pokemon.abilities.joinToString(", ") { it.ability.name.replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
            } }

            pokemonTypes.removeAllViews()

            pokemon.types.forEach { type ->
                val typeTextView = TextView(this@DetailPokemonActivity).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    text = type.type.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    textSize = resources.getDimension(R.dimen.pokemon_type_text_size)
                    setTextColor(Color.WHITE)
                    gravity = Gravity.CENTER
                    setPadding(
                        resources.getDimensionPixelSize(R.dimen.pokemon_type_padding_horizontal),
                        resources.getDimensionPixelSize(R.dimen.pokemon_type_padding_vertical),
                        resources.getDimensionPixelSize(R.dimen.pokemon_type_padding_horizontal),
                        resources.getDimensionPixelSize(R.dimen.pokemon_type_padding_vertical)
                    )

                    val backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_background)?.mutate()
                    val backgroundColorResId = when (type.type.name) {
                        "fire" -> R.color.type_fire
                        "water" -> R.color.type_water
                        "electric" -> R.color.type_electric
                        "grass" -> R.color.type_grass
                        "ice" -> R.color.type_ice
                        "fighting" -> R.color.type_fighting
                        "poison" -> R.color.type_poison
                        "ground" -> R.color.type_ground
                        "flying" -> R.color.type_flying
                        "psychic" -> R.color.type_psychic
                        "bug" -> R.color.type_bug
                        "rock" -> R.color.type_rock
                        "ghost" -> R.color.type_ghost
                        "dragon" -> R.color.type_dragon
                        "dark" -> R.color.type_dark
                        "steel" -> R.color.type_steel
                        "fairy" -> R.color.type_fairy
                        else -> R.color.type_normal
                    }
                    backgroundDrawable?.setTint(ContextCompat.getColor(context, backgroundColorResId))
                    background = backgroundDrawable
                }

                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(
                        resources.getDimensionPixelSize(R.dimen.pokemon_type_margin_horizontal),
                        0,
                        resources.getDimensionPixelSize(R.dimen.pokemon_type_margin_horizontal),
                        0
                    )
                }
                typeTextView.layoutParams = params

                pokemonTypes.addView(typeTextView)
            }

            populateStats(pokemon)
        }
    }

    private fun populateStats(pokemon: Pokemon) {
        val statsLayout = binding.pokemonStats
        statsLayout.removeAllViews()

        pokemon.stats.forEach { stat ->
            val statLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setPadding(0, 8, 0, 8)
            }

            val statName = TextView(this).apply {
                text = when (stat.stat.name) {
                    "hp" -> "HP"
                    "attack" -> "ATK"
                    "defense" -> "DEF"
                    "special-attack" -> "SP-ATK"
                    "special-defense" -> "SP-DEF"
                    "speed" -> "SPD"
                    else -> stat.stat.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                }
                textSize = 18f
                setTextColor(Color.DKGRAY)
                setTypeface(null, Typeface.BOLD)
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2f
                )
                max = 100
                progress = stat.base_stat
                progressDrawable = ContextCompat.getDrawable(this@DetailPokemonActivity, R.drawable.progress_bar_with_border)?.mutate()
                progressDrawable?.setTint(getStatColor(stat.stat.name))
            }

            statLayout.addView(statName)
            statLayout.addView(progressBar)
            statsLayout.addView(statLayout)
        }
    }

    private fun getStatColor(statName: String): Int {
        return when (statName) {
            "hp" -> ContextCompat.getColor(this, R.color.hp_color)
            "attack" -> ContextCompat.getColor(this, R.color.attack_color)
            "defense" -> ContextCompat.getColor(this, R.color.defense_color)
            "special-attack" -> ContextCompat.getColor(this, R.color.special_attack_color)
            "special-defense" -> ContextCompat.getColor(this, R.color.special_defense_color)
            "speed" -> ContextCompat.getColor(this, R.color.speed_color)
            else -> Color.GRAY
        }
    }
}