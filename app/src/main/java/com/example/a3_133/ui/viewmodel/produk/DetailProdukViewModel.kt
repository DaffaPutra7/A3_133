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
    private val pr: ProdukRepository
) : ViewModel() {
    private val _idProduk: String = checkNotNull(savedStateHandle["id_produk"])

    private val _detailProdukUiState = MutableStateFlow<DetailProdukUiState>(DetailProdukUiState.Loading)
    val detailProdukUiState: StateFlow<DetailProdukUiState> = _detailProdukUiState.asStateFlow()

    // Init = Program yang pertama kali dijalankan, yang ada didalam {}
    init {
        getProdukById(_idProduk)
    }

    private fun getProdukById(idProduk: String) {
        viewModelScope.launch {
            _detailProdukUiState.value = DetailProdukUiState.Loading
            _detailProdukUiState.value = try {
                val produk = pr.getProdukById(idProduk)
                DetailProdukUiState.Success(produk)
            } catch (e: IOException) {
                DetailProdukUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailProdukUiState.Error("Terjadi kesalahan server")
            }
        }
    }
}