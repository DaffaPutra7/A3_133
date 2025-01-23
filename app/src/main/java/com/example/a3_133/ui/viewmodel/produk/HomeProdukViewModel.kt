package com.example.a3_133.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a3_133.model.Produk
import com.example.a3_133.repository.ProdukRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeProdukUiState {
    data class Success(val produk: List<Produk>) : HomeProdukUiState()
    object Error : HomeProdukUiState()
    object Loading : HomeProdukUiState()
}

class HomeProdukViewModel(private val produk: ProdukRepository): ViewModel() {
    var produkUIState : HomeProdukUiState by mutableStateOf(HomeProdukUiState.Loading)
        private set

    init {
        getProduk()
    }

    fun getProduk() {
        viewModelScope.launch {
            produkUIState = HomeProdukUiState.Loading
            produkUIState = try {
                HomeProdukUiState.Success(produk.getProduk())
            } catch (e:IOException) {
                HomeProdukUiState.Error
            } catch (e: HttpException) {
                HomeProdukUiState.Error
            }
        }
    }

    fun deleteProduk(id_produk: String) {
        viewModelScope.launch {
            try {
                produk.deleteProduk(id_produk)
                getProduk() // Refresh
            } catch (e: IOException) {
                produkUIState = HomeProdukUiState.Error
            } catch (e: HttpException) {
                produkUIState = HomeProdukUiState.Error
            }
        }
    }
}