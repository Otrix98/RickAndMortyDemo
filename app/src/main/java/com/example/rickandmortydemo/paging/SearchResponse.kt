package com.example.rickandmortydemo.paging

import com.example.rickandmortydemo.data.models.Character

data class SearchResponse(
    val results: List<Character> = emptyList()
)
