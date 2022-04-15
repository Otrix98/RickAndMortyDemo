package com.example.rickandmortydemo.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CharacterDatabase.CHARACTERS_REMOTE_TABLE)
data class RemoteKeys(
    @PrimaryKey
    val itemId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
