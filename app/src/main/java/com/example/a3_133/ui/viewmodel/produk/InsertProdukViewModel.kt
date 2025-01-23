package com.example.a3_133.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Produk
import com.example.a3_133.repository.ProdukRepository
import kotlinx.coroutines.launch

class InsertProdukViewModel(private val produk: ProdukRepository) : ViewModel() {

    var produkuiState by mutableStateOf(InsertProdukUiState())
        private set

    fun updateInsertProdukState(insertProdukUiEvent: InsertProdukUiEvent){
        produkuiState = InsertProdukUiState(insertProdukUiEvent = insertProdukUiEvent)
    }

    suspend fun insertProduk(){
        viewModelScope.launch {
            try {
                produk.insertProduk(produkuiState.insertProdukUiEvent.toProduk())
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertProdukUiState(
    val insertProdukUiEvent: InsertProdukUiEvent = InsertProdukUiEvent()
)

data class InsertProdukUiEvent(
    val idProduk: String = "",
    val namaProduk: String = "",
    val deskripsiProduk: String = "",
    val harga: Double = 0.0,
    val stok: Int = 0,
    val idKategori: String = "",
    val idPemasok: String = "",
    val idMerk: String = "",
)

fun InsertProdukUiEvent.toProduk(): Produk = Produk(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)

fun Produk.toUiStateProduk(): InsertProdukUiState = InsertProdukUiState(
    insertProdukUiEvent = toInsertProdukUiEvent()
)

fun Produk.toInsertProdukUiEvent(): InsertProdukUiEvent = InsertProdukUiEvent(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)