package com.example.a3_133.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Produk
import com.example.a3_133.repository.KategoriRepository
import com.example.a3_133.repository.MerkRepository
import com.example.a3_133.repository.PemasokRepository
import com.example.a3_133.repository.ProdukRepository
import kotlinx.coroutines.launch

class UpdateProdukViewModel(
    private val pr: ProdukRepository,
    private val kategoriRepository: KategoriRepository,
    private val pemasokRepository: PemasokRepository,
    private val merkRepository: MerkRepository
) : ViewModel() {

    var produkuiState by mutableStateOf(UpdateProdukUiState())
        private set

    var kategoriList by mutableStateOf<List<String>>(emptyList())
        private set

    var pemasokList by mutableStateOf<List<String>>(emptyList())
        private set

    var merkList by mutableStateOf<List<String>>(emptyList())
        private set

    fun updateProdukState(updateProdukUiEvent: UpdateProdukUiEvent) {
        produkuiState = produkuiState.copy(updateProdukUiEvent = updateProdukUiEvent)
    }

    fun getProdukById(idProduk: String) {
        viewModelScope.launch {
            try {
                val produk = pr.getProdukById(idProduk)
                produkuiState = UpdateProdukUiState(updateProdukUiEvent = produk.toUpdateProdukUiEvent())
                fetchData()
            } catch (e: Exception) {
                e.printStackTrace()
                produkuiState = UpdateProdukUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                kategoriList = kategoriRepository.getKategori().map { it.namaKategori }
                pemasokList = pemasokRepository.getPemasok().map { it.namaPemasok }
                merkList = merkRepository.getMerk().map { it.namaMerk }
            } catch (e: Exception) {
                kategoriList = emptyList()
                pemasokList = emptyList()
                merkList = emptyList()
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
    val harga: String = "",
    val stok: String = "",
    val kategori: String = "",
    val pemasok: String = "",
    val merk: String = "",
)

fun UpdateProdukUiEvent.toProduk(): Produk = Produk(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    kategori = kategori,
    pemasok = pemasok,
    merk = merk
)

fun Produk.toUpdateProdukUiEvent(): UpdateProdukUiEvent = UpdateProdukUiEvent(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    kategori = kategori,
    pemasok = pemasok,
    merk = merk
)