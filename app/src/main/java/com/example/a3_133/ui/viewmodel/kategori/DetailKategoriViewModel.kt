package com.example.a3_133.ui.viewmodel.kategori

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a3_133.model.Kategori
import com.example.a3_133.repository.KategoriRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailKategoriUiState {
    data class Success(val kategori: Kategori) : DetailKategoriUiState()
    data class Error(val message: String) : DetailKategoriUiState()
    object Loading : DetailKategoriUiState()
}

class DetailKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {
    private val _idKategori: String = checkNotNull(savedStateHandle["id"])

    private val _detailKategoriUiState = MutableStateFlow<DetailKategoriUiState>(DetailKategoriUiState.Loading)
    val detailKategoriUiState: StateFlow<DetailKategoriUiState> = _detailKategoriUiState.asStateFlow()

    init {
        getKategoriById(_idKategori)
    }

    fun getKategoriById(idKategori: String) {
        viewModelScope.launch {
            _detailKategoriUiState.value = DetailKategoriUiState.Loading
            _detailKategoriUiState.value = try {
                val kategori = kategoriRepository.getKategoriById(idKategori)
                DetailKategoriUiState.Success(kategori)
            } catch (e: IOException) {
                DetailKategoriUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailKategoriUiState.Error("Terjadi kesalahan server")
            }
        }
    }
}