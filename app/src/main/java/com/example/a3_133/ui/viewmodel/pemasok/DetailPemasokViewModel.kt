package com.example.a3_133.ui.viewmodel.pemasok

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a3_133.model.Pemasok
import com.example.a3_133.repository.PemasokRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPemasokUiState {
    data class Success(val pemasok: Pemasok) : DetailPemasokUiState()
    data class Error(val message: String) : DetailPemasokUiState()
    object Loading : DetailPemasokUiState()
}

class DetailPemasokViewModel(
    savedStateHandle: SavedStateHandle,
    private val pemasokRepository: PemasokRepository
) : ViewModel() {
    private val _idPemasok: String = checkNotNull(savedStateHandle["id"])

    private val _detailPemasokUiState = MutableStateFlow<DetailPemasokUiState>(DetailPemasokUiState.Loading)
    val detailPemasokUiState: StateFlow<DetailPemasokUiState> = _detailPemasokUiState.asStateFlow()

    init {
        getPemasokById(_idPemasok)
    }

    fun getPemasokById(idPemasok: String) {
        viewModelScope.launch {
            _detailPemasokUiState.value = DetailPemasokUiState.Loading
            _detailPemasokUiState.value = try {
                val pemasok = pemasokRepository.getPemasokById(idPemasok)
                DetailPemasokUiState.Success(pemasok)
            } catch (e: IOException) {
                DetailPemasokUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailPemasokUiState.Error("Terjadi kesalahan server")
            }
        }
    }
}