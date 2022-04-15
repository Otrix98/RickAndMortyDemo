package com.example.rickandmortydemo.ui.viemodels

import androidx.lifecycle.*
import androidx.paging.*
import com.example.rickandmortydemo.data.Repository
import com.example.rickandmortydemo.data.models.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<Character>>

    init {
        pagingDataFlow = searchNews()
    }

    private fun searchNews(): Flow<PagingData<Character>> =
        repository.getSearchResultStream()
}






