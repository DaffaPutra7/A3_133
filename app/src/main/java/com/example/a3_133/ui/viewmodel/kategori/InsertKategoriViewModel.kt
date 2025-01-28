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

class InsertKategoriViewModel(private val kategori: KategoriRepository) : ViewModel() {

    var kategoriuiState by mutableStateOf(InsertKategoriUiState())
        private set

    fun updateInsertKategoriState(insertkategoriUiEvent: InsertKategoriUiEvent) {
        kategoriuiState = kategoriuiState.copy(
            insertKategoriUiEvent = insertkategoriUiEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = kategoriuiState.insertKategoriUiEvent
        val errorState = KategoriErrorState(
            namaKategori = if (event.namaKategori.isNotEmpty()) null else "Nama Kategori tidak boleh kosong",
            deskripsiKategori = if (event.deskripsiKategori.isNotEmpty()) null else "Deskripsi Kategori tidak boleh kosong"
        )
        kategoriuiState = kategoriuiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertKategori() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    kategori.insertKategori(kategoriuiState.insertKategoriUiEvent.toKategori())
                    kategoriuiState = kategoriuiState.copy(
                        snackBarMessage = "Data Kategori Berhasil Disimpan",
                        insertKategoriUiEvent = InsertKategoriUiEvent(),
                        isEntryValid = KategoriErrorState()
                    )
                    delay(8000)
                    resetSnackBarMessage()
                } catch (e: Exception) {
                    kategoriuiState = kategoriuiState.copy(snackBarMessage = "Data Kategori Gagal Disimpan")
                }
                delay(3000)
                resetSnackBarMessage()
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

data class InsertKategoriUiState(
    val insertKategoriUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent(),
    val isEntryValid: KategoriErrorState = KategoriErrorState(),
    val snackBarMessage: String? = null
)

data class KategoriErrorState(
    val namaKategori: String? = null,
    val deskripsiKategori: String? = null
) {
    fun isValid(): Boolean {
        return namaKategori == null && deskripsiKategori == null
    }
}

data class InsertKategoriUiEvent(
    val idKategori: String = "",
    val namaKategori: String = "",
    val deskripsiKategori: String = ""
)

fun InsertKategoriUiEvent.toKategori(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)

fun Kategori.toUiStateKategori(): InsertKategoriUiState = InsertKategoriUiState(
    insertKategoriUiEvent = toInsertKategoriUiEvent()
)

fun Kategori.toInsertKategoriUiEvent(): InsertKategoriUiEvent = InsertKategoriUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)