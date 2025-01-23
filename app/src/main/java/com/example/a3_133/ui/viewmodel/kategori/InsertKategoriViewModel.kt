package com.example.a3_133.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Kategori
import com.example.a3_133.repository.KategoriRepository
import kotlinx.coroutines.launch

class InsertKategoriViewModel(private val kategori: KategoriRepository) : ViewModel() {

    var kategoriuiState by mutableStateOf(InsertKategoriUiState())
        private set

    fun updateInsertKategoriState(insertkategoriUiEvent: InsertKategoriUiEvent){
        kategoriuiState = InsertKategoriUiState(insertKategoriUiEvent = insertkategoriUiEvent)
    }

    suspend fun insertKategori(){
        viewModelScope.launch {
            try {
                kategori.insertKategori(kategoriuiState.insertKategoriUiEvent.toKategori())
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertKategoriUiState(
    val insertKategoriUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent()
)

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