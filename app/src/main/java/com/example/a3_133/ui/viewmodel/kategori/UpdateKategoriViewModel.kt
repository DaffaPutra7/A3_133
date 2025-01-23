package com.example.a3_133.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Kategori
import com.example.a3_133.repository.KategoriRepository
import kotlinx.coroutines.launch

class UpdateKategoriViewModel(
    private val kt: KategoriRepository
) : ViewModel() {

    var kategoriuiState by mutableStateOf(UpdateKategoriUiState())
        private set

    fun updateKategoriState(updateKategoriUiEvent: UpdateKategoriUiEvent) {
        kategoriuiState = kategoriuiState.copy(updateKategoriUiEvent = updateKategoriUiEvent)
    }

    fun getKategoriById(idKategori: String) {
        viewModelScope.launch {
            try {
                val kategori = kt.getKategoriById(idKategori)
                kategoriuiState = UpdateKategoriUiState(updateKategoriUiEvent = kategori.toUpdateKategoriUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                kategoriuiState = UpdateKategoriUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateKategori() {
        viewModelScope.launch {
            try {
                val kategori = kategoriuiState.updateKategoriUiEvent.toKategori()
                kt.updateKategori(kategori.idKategori, kategori)
                kategoriuiState = kategoriuiState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                kategoriuiState = kategoriuiState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}

data class UpdateKategoriUiState(
    val updateKategoriUiEvent: UpdateKategoriUiEvent = UpdateKategoriUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateKategoriUiEvent(
    val idKategori: String = "",
    val namaKategori: String = "",
    val deskripsiKategori: String = ""
)

fun UpdateKategoriUiEvent.toKategori(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)

fun Kategori.toUpdateKategoriUiEvent(): UpdateKategoriUiEvent = UpdateKategoriUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)