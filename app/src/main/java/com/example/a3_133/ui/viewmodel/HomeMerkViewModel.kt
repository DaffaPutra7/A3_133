package com.example.a3_133.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a3_133.model.Merk
import com.example.a3_133.repository.MerkRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val merk: List<Merk>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeMerkViewModel(private val merk: MerkRepository): ViewModel() {
    var merkUIState : HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMerk()
    }

    fun getMerk() {
        viewModelScope.launch {
            merkUIState =  HomeUiState.Loading
            merkUIState = try {
                HomeUiState.Success(merk.getMerk())
            } catch (e:IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteMerk(id_merk: String) {
        viewModelScope.launch {
            try {
                merk.deleteMerk(id_merk)
                getMerk() // Refresh
            } catch (e: IOException) {
                merkUIState = HomeUiState.Error
            } catch (e: HttpException) {
                merkUIState = HomeUiState.Error
            }
        }
    }
}