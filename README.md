# PokeWorld

A Pokédex for Android, built with Kotlin on top of [PokeAPI](https://pokeapi.co). Final project for the Android Beginner class at KSM Android UPN Veteran Jakarta.

This is a fan-made app and is not affiliated with Nintendo.

## Features

- List of the first 50 Pokémon
- Filter by type
- Detail screen with types, height, weight, abilities and base stats
- Bottom navigation across Home, Pokédex and Profile

## Tech stack

- Kotlin and XML layouts
- ViewModel and LiveData
- Retrofit and Gson for the API
- Glide for images
- RecyclerView

## Getting started

1. Open the project in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on a device or emulator (Android 7.0, API 24, or newer).

The app needs an internet connection and no API key.

## Project structure

```
app/src/main/java/com/example/pokedex/
  data/retrofit/   API service, models, repository and ViewModel
  ui/              Activities, fragments and the list adapter
```
