package com.example.a3_133.ui.viewmodel.pemasok

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Pemasok
import com.example.a3_133.repository.PemasokRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InsertPemasokViewModel(private val pemasok: PemasokRepository) : ViewModel() {

    var pemasokuiState by mutableStateOf(InsertPemasokUiState())
        private set

    fun updateInsertPemasokState(insertpemasokUiEvent: InsertPemasokUiEvent) {
        pemasokuiState = pemasokuiState.copy(
            insertPemasokUiEvent = insertpemasokUiEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = pemasokuiState.insertPemasokUiEvent
        val errorState = PemasokErrorState(
            namaPemasok = if (event.namaPemasok.isNotEmpty()) null else "Nama Pemasok tidak boleh kosong",
            alamatPemasok = if (event.alamatPemasok.isNotEmpty()) null else "Alamat Pemasok tidak boleh kosong",
            teleponPemasok = if (event.teleponPemasok.isNotEmpty()) null else "Telepon Pemasok tidak boleh kosong"
        )
        pemasokuiState = pemasokuiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertPemasok() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pemasok.insertPemasok(pemasokuiState.insertPemasokUiEvent.toPemasok())
                    pemasokuiState = pemasokuiState.copy(
                        snackBarMessage = "Data Pemasok Berhasil Disimpan",
                        insertPemasokUiEvent = InsertPemasokUiEvent(),
                        isEntryValid = PemasokErrorState()
                    )
                    delay(3000)
                } catch (e: Exception) {
                    pemasokuiState = pemasokuiState.copy(snackBarMessage = "Data Pemasok Gagal Disimpan")
                }
                delay(3000)
                resetSnackBarMessage()
            }
        } else {
            pemasokuiState = pemasokuiState.copy(snackBarMessage = "Input tidak valid. Periksa kembali data pemasok Anda.")
            viewModelScope.launch {
                delay(3000)
                resetSnackBarMessage()
            }
        }
    }

    fun resetSnackBarMessage() {
        pemasokuiState = pemasokuiState.copy(snackBarMessage = null)
    }
}

data class InsertPemasokUiState(
    val insertPemasokUiEvent: InsertPemasokUiEvent = InsertPemasokUiEvent(),
    val isEntryValid: PemasokErrorState = PemasokErrorState(),
    val snackBarMessage: String? = null
)

data class PemasokErrorState(
    val namaPemasok: String? = null,
    val alamatPemasok: String? = null,
    val teleponPemasok: String? = null
) {
    fun isValid(): Boolean {
        return namaPemasok == null && alamatPemasok == null && teleponPemasok == null
    }
}

data class InsertPemasokUiEvent(
    val idPemasok: String = "",
    val namaPemasok: String = "",
    val alamatPemasok: String = "",
    val teleponPemasok: String = ""
)

fun InsertPemasokUiEvent.toPemasok(): Pemasok = Pemasok(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)

fun Pemasok.toUiStatePemasok(): InsertPemasokUiState = InsertPemasokUiState(
    insertPemasokUiEvent = toInsertPemasokUiEvent()
)

fun Pemasok.toInsertPemasokUiEvent(): InsertPemasokUiEvent = InsertPemasokUiEvent(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)