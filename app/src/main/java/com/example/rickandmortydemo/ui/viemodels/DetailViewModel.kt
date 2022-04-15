package com.example.rickandmortydemo.ui.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortydemo.data.Repository
import com.example.rickandmortydemo.data.models.CharacterDetail
import com.example.rickandmortydemo.data.models.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val itemDetails =
        MutableLiveData(CharacterDetail(0, "", "", "", "", "", Location(""), listOf("")))
    val itemsList: LiveData<CharacterDetail>
        get() = itemDetails

    fun getDetails(id: Int) {
        viewModelScope.launch {
            val details = repository.getCharacterDetails(id)
            itemDetails.postValue(details)
        }
    }
}