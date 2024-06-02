package com.example.pokedex.data.retrofit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val base_experience: Int,
    val height: Int,
    val is_default: Boolean,
    val order: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val held_items: List<HeldItem>,
    val location_area_encounters: String,
    val moves: List<Move>,
    val species: Species,
    val stats: List<Stat>,
    val types: List<Type>
) : Parcelable

@Parcelize
data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
) : Parcelable

@Parcelize
data class AbilityX(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Form(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class GameIndice(
    val game_index: Int,
    val version: Version
) : Parcelable

@Parcelize
data class Version(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
) : Parcelable

@Parcelize
data class Item(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class VersionDetail(
    val rarity: Int,
    val version: VersionX
) : Parcelable

@Parcelize
data class VersionX(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
) : Parcelable

@Parcelize
data class MoveX(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val version_group: VersionGroup
) : Parcelable

@Parcelize
data class MoveLearnMethod(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class VersionGroup(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Species(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Sprites(
    val back_default: String,
    val back_female: String?,
    val back_shiny: String,
    val back_shiny_female: String?,
    val front_default: String,
    val front_female: String?,
    val front_shiny: String,
    val front_shiny_female: String?
) : Parcelable

@Parcelize
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
) : Parcelable

@Parcelize
data class StatX(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Type(
    val slot: Int,
    val type: TypeX
) : Parcelable

@Parcelize
data class TypeX(
    val name: String,
    val url: String
) : Parcelable
