package com.example.a3_133.ui.viewmodel.produk

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a3_133.model.Produk
import com.example.a3_133.repository.ProdukRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailProdukUiState {
    data class Success(val produk: Produk) : DetailProdukUiState()
    data class Error(val message: String) : DetailProdukUiState()
    object Loading : DetailProdukUiState()
}

class DetailProdukViewModel(
    savedStateHandle: SavedStateHandle,
    private val produkRepository: ProdukRepository
) : ViewModel() {
    private val _idProduk: String = checkNotNull(savedStateHandle["id"])

    private val _detailProdukUiState = MutableStateFlow<DetailProdukUiState>(DetailProdukUiState.Loading)
    val detailProdukUiState: StateFlow<DetailProdukUiState> = _detailProdukUiState.asStateFlow()

    var updateTrigger = MutableStateFlow(false)
        private set

    init {
        getProdukById(_idProduk)
    }

    fun getProdukById(idProduk: String) {
        viewModelScope.launch {
            _detailProdukUiState.value = DetailProdukUiState.Loading
            _detailProdukUiState.value = try {
                val produk = produkRepository.getProdukById(idProduk)
                DetailProdukUiState.Success(produk)
            } catch (e: IOException) {
                DetailProdukUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailProdukUiState.Error("Terjadi kesalahan server")
            }
        }
    }

    fun updateData() {
        viewModelScope.launch {
            // Lakukan update data
            // Setelah selesai, ubah nilai updateTrigger untuk memicu refresh data
            updateTrigger.value = !updateTrigger.value
        }
    }
}