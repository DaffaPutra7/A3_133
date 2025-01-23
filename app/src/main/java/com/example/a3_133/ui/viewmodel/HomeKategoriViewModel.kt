package com.example.a3_133.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a3_133.model.Kategori
import com.example.a3_133.repository.KategoriRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeKategoriUiState {
    data class Success(val kategori: List<Kategori>) : HomeKategoriUiState()
    object Error : HomeKategoriUiState()
    object Loading : HomeKategoriUiState()
}

class HomeKategoriViewModel(private val kategori: KategoriRepository): ViewModel() {
    var kategoriUIState : HomeKategoriUiState by mutableStateOf(HomeKategoriUiState.Loading)
        private set

    init {
        getKategori()
    }

    fun getKategori() {
        viewModelScope.launch {
            kategoriUIState =  HomeKategoriUiState.Loading
            kategoriUIState = try {
                HomeKategoriUiState.Success(kategori.getKategori())
            } catch (e:IOException) {
                HomeKategoriUiState.Error
            } catch (e: HttpException) {
                HomeKategoriUiState.Error
            }
        }
    }

    fun deleteKategori(id_kategori: String) {
        viewModelScope.launch {
            try {
                kategori.deleteKategori(id_kategori)
                getKategori() // Refresh
            } catch (e: IOException) {
                kategoriUIState = HomeKategoriUiState.Error
            } catch (e: HttpException) {
                kategoriUIState = HomeKategoriUiState.Error
            }
        }
    }
}