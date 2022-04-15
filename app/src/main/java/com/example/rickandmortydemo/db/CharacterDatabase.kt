package com.example.rickandmortydemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortydemo.data.models.Character


@Database(
    entities = [Character::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object Constants {
        const val DB_NAME = "charactersDatabase"
        const val CHARACTERS_TABLE = "characters_item_table"
        const val CHARACTERS_REMOTE_TABLE = "characters_remote_keys_table"
    }
}
