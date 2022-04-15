package com.example.rickandmortydemo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmortydemo.db.CharacterDatabase


@Entity(tableName = CharacterDatabase.CHARACTERS_TABLE)
data class Character (
    @PrimaryKey
    val id: Int,
    val name: String,
    val species: String?,
    val gender: String?,
    val image: String?
    )