package com.example.rickandmortydemo.data.models

import androidx.room.Embedded
import androidx.room.PrimaryKey

data class CharacterDetail (
    @PrimaryKey
    val id: Int,
    val name: String,
    val species: String?,
    val gender: String?,
    val image: String?,
    val status: String,
    @Embedded
    val location: Location,
    val episode: List<String>?,
    )