package com.example.a3_133.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Kategori
import com.example.a3_133.repository.KategoriRepository
import kotlinx.coroutines.delay
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

    private fun validateFields(): Boolean {
        val event = kategoriuiState.updateKategoriUiEvent
        val errorState = UpdateKategoriErrorState(
            namaKategori = if (event.namaKategori.isNotEmpty()) null else "Nama Kategori tidak boleh kosong",
            deskripsiKategori = if (event.deskripsiKategori.isNotEmpty()) null else "Deskripsi Kategori tidak boleh kosong"
        )
        kategoriuiState = kategoriuiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateKategori() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    val kategori = kategoriuiState.updateKategoriUiEvent.toKategori()
                    kt.updateKategori(kategori.idKategori, kategori)
                    kategoriuiState = kategoriuiState.copy(isSuccess = true, snackBarMessage = "Data Kategori Berhasil Diperbarui")
                    delay(3000)
                    resetSnackBarMessage()
                } catch (e: Exception) {
                    e.printStackTrace()
                    kategoriuiState = kategoriuiState.copy(isError = true, errorMessage = e.message)
                    delay(3000)
                    resetSnackBarMessage()
                }
            }
        } else {
            kategoriuiState = kategoriuiState.copy(snackBarMessage = "Input tidak valid. Periksa kembali data kategori Anda.")
            viewModelScope.launch {
                delay(3000)
                resetSnackBarMessage()
            }
        }
    }

    fun resetSnackBarMessage() {
        kategoriuiState = kategoriuiState.copy(snackBarMessage = null)
    }
}

data class UpdateKategoriUiState(
    val updateKategoriUiEvent: UpdateKategoriUiEvent = UpdateKategoriUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isEntryValid: UpdateKategoriErrorState = UpdateKategoriErrorState(),
    val snackBarMessage: String? = null
)

data class UpdateKategoriErrorState(
    val namaKategori: String? = null,
    val deskripsiKategori: String? = null
) {
    fun isValid(): Boolean {
        return namaKategori == null && deskripsiKategori == null
    }
}

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