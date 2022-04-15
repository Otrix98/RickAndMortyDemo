package com.example.rickandmortydemo.networking

import com.example.rickandmortydemo.data.models.CharacterDetail
import com.example.rickandmortydemo.paging.SearchResponse
import retrofit2.http.*

interface NewsApi {

    @GET("character")
    suspend fun searchItems(
        @Query("page") page: Int,
    ): SearchResponse

    @GET("character/{id}")
    suspend fun getDetails(
        @Path("id") id: Int,
    ): CharacterDetail
}

