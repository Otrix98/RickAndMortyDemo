package com.example.rickandmortydemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM characters_remote_keys_table WHERE itemId = :itemId")
    suspend fun remoteKeysRepoId(itemId: Int): RemoteKeys?

    @Query("DELETE FROM characters_remote_keys_table")
    suspend fun clearRemoteKeys()
}
