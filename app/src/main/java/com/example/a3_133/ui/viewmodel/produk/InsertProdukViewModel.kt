package com.example.a3_133.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Kategori
import com.example.a3_133.model.Merk
import com.example.a3_133.model.Produk
import com.example.a3_133.repository.KategoriRepository
import com.example.a3_133.repository.MerkRepository
import com.example.a3_133.repository.PemasokRepository
import com.example.a3_133.repository.ProdukRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InsertProdukViewModel(
    private val produk: ProdukRepository,
    private val kategori: KategoriRepository,
    private val pemasok: PemasokRepository,
    private val merk: MerkRepository
) : ViewModel() {

    var produkuiState by mutableStateOf(InsertProdukUiState())
        private set

    var kategoriList by mutableStateOf<List<String>>(emptyList())
        private set

    var pemasokList by mutableStateOf<List<String>>(emptyList())
        private set

    var merkList by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                val kategoriData = kategori.getKategori().map { it.namaKategori }
                val pemasokData = pemasok.getPemasok().map { it.namaPemasok }
                val merkData = merk.getMerk().map { it.namaMerk }

                kategoriList = kategoriData
                pemasokList = pemasokData
                merkList = merkData
            } catch (e: Exception) {
                kategoriList = emptyList()
                pemasokList = emptyList()
                merkList = emptyList()
            }
        }
    }

    fun updateInsertProdukState(insertProdukUiEvent: InsertProdukUiEvent){
        produkuiState = InsertProdukUiState(insertProdukUiEvent = insertProdukUiEvent)
    }

    suspend fun insertProduk() {
        viewModelScope.launch {
            try {
                produk.insertProduk(produkuiState.insertProdukUiEvent.toProduk())
            } catch (e: Exception) {
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
    val harga: String = "",
    val stok: String = "",
    val kategori: String = "",
    val pemasok: String = "",
    val merk: String = ""
)

fun InsertProdukUiEvent.toProduk(): Produk = Produk(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    kategori = kategori,
    pemasok = pemasok,
    merk = merk
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
    kategori = kategori,
    pemasok = pemasok,
    merk = merk
)