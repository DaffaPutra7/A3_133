package com.example.a3_133.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Produk
import com.example.a3_133.repository.ProdukRepository
import kotlinx.coroutines.launch

class UpdateProdukViewModel(
    private val pr: ProdukRepository
) : ViewModel() {

    var produkuiState by mutableStateOf(UpdateProdukUiState())
        private set

    fun updateProdukState(updateProdukUiEvent: UpdateProdukUiEvent) {
        produkuiState = produkuiState.copy(updateProdukUiEvent = updateProdukUiEvent)
    }

    fun getProdukById(idProduk: String) {
        viewModelScope.launch {
            try {
                val produk = pr.getProdukById(idProduk)
                produkuiState = UpdateProdukUiState(updateProdukUiEvent = produk.toUpdateProdukUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                produkuiState = UpdateProdukUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateProduk() {
        viewModelScope.launch {
            try {
                val produk = produkuiState.updateProdukUiEvent.toProduk()
                pr.updateProduk(produk.idProduk, produk)
                produkuiState = produkuiState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                produkuiState = produkuiState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}

data class UpdateProdukUiState(
    val updateProdukUiEvent: UpdateProdukUiEvent = UpdateProdukUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateProdukUiEvent(
    val idProduk: String = "",
    val namaProduk: String = "",
    val deskripsiProduk: String = "",
    val harga: Double = 0.0,
    val stok: Int = 0,
    val idKategori: String = "",
    val idPemasok: String = "",
    val idMerk: String = "",
)

fun UpdateProdukUiEvent.toProduk(): Produk = Produk(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)

fun Produk.toUpdateProdukUiEvent(): UpdateProdukUiEvent = UpdateProdukUiEvent(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)