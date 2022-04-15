package com.example.rickandmortydemo.di

import android.app.Application
import androidx.room.Room
import com.example.rickandmortydemo.db.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): CharacterDatabase {
        return Room.databaseBuilder(
            application,
            CharacterDatabase::class.java,
            CharacterDatabase.DB_NAME
        )
            .build()
    }
}