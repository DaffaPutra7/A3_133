package com.example.a3_133.ui.viewmodel.pemasok

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a3_133.model.Pemasok
import com.example.a3_133.repository.PemasokRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePemasokUiState {
    data class Success(val pemasok: List<Pemasok>) : HomePemasokUiState()
    object Error : HomePemasokUiState()
    object Loading : HomePemasokUiState()
}

class HomePemasokViewModel(private val pemasok: PemasokRepository): ViewModel() {
    var pemasokUIState : HomePemasokUiState by mutableStateOf(HomePemasokUiState.Loading)
        private set

    init {
        getPemasok()
    }

    fun getPemasok() {
        viewModelScope.launch {
            pemasokUIState = HomePemasokUiState.Loading
            pemasokUIState = try {
                HomePemasokUiState.Success(pemasok.getPemasok())
            } catch (e:IOException) {
                HomePemasokUiState.Error
            } catch (e: HttpException) {
                HomePemasokUiState.Error
            }
        }
    }

    fun deletePemasok(id_pemasok: String) {
        viewModelScope.launch {
            try {
                pemasok.deletePemasok(id_pemasok)
                getPemasok() // Refresh
            } catch (e: IOException) {
                pemasokUIState = HomePemasokUiState.Error
            } catch (e: HttpException) {
                pemasokUIState = HomePemasokUiState.Error
            }
        }
    }
}