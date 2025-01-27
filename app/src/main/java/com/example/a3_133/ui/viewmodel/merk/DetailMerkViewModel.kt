package com.example.a3_133.ui.viewmodel.merk

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a3_133.model.Merk
import com.example.a3_133.repository.MerkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailMerkUiState {
    data class Success(val merk: Merk) : DetailMerkUiState()
    data class Error(val message: String) : DetailMerkUiState()
    object Loading : DetailMerkUiState()
}

class DetailMerkViewModel(
    savedStateHandle: SavedStateHandle,
    private val merkRepository: MerkRepository
) : ViewModel() {
    private val _idMerk: String = checkNotNull(savedStateHandle["id"])

    private val _detailMerkUiState = MutableStateFlow<DetailMerkUiState>(DetailMerkUiState.Loading)
    val detailMerkUiState: StateFlow<DetailMerkUiState> = _detailMerkUiState.asStateFlow()

    init {
        getMerkById(_idMerk)
    }

    fun getMerkById(idMerk: String) {
        viewModelScope.launch {
            _detailMerkUiState.value = DetailMerkUiState.Loading
            _detailMerkUiState.value = try {
                val merk = merkRepository.getMerkById(idMerk)
                DetailMerkUiState.Success(merk)
            } catch (e: IOException) {
                DetailMerkUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailMerkUiState.Error("Terjadi kesalahan server")
            }
        }
    }
}