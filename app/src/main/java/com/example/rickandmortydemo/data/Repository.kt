package com.example.rickandmortydemo.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortydemo.networking.NewsApi
import com.example.rickandmortydemo.data.models.Character
import com.example.rickandmortydemo.data.models.CharacterDetail
import com.example.rickandmortydemo.db.CharacterDatabase
import com.example.rickandmortydemo.paging.RemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: NewsApi,
    private val dataBase: CharacterDatabase
) {

    fun getSearchResultStream(): Flow<PagingData<Character>> {
        val pagingSourceFactory = { dataBase.charactersDao().itemsByName() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = RemoteMediator(
                api,
                dataBase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getCharacterDetails(id: Int): CharacterDetail {
        return api.getDetails(id)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}