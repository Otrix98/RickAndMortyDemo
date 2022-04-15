package com.example.rickandmortydemo.ui.adapters

import com.example.rickandmortydemo.data.models.Character


interface AdaptersListener {
    fun onClickItem(item: Character)
}