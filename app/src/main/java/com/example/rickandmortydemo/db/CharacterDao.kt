package com.example.rickandmortydemo.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortydemo.data.models.Character

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Character>)

    @Query("SELECT * FROM characters_item_table")
    fun itemsByName(): PagingSource<Int, Character>

    @Query("DELETE FROM characters_item_table")
    suspend fun clearItems()
}
