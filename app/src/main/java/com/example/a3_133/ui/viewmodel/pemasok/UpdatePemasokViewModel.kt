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

class UpdatePemasokViewModel(
    private val pmk: PemasokRepository
) : ViewModel() {

    var pemasokuiState by mutableStateOf(UpdatePemasokUiState())
        private set

    fun updatePemasokState(updatepemasokUiEvent: UpdatePemasokUiEvent) {
        pemasokuiState = pemasokuiState.copy(updatePemasokUiEvent = updatepemasokUiEvent)
    }

    fun getPemasokById(idPemasok: String) {
        viewModelScope.launch {
            try {
                val pemasok = pmk.getPemasokById(idPemasok)
                pemasokuiState = UpdatePemasokUiState(updatePemasokUiEvent = pemasok.toUpdatePemasokUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                pemasokuiState = UpdatePemasokUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    private fun validateFields(): Boolean {
        val event = pemasokuiState.updatePemasokUiEvent
        val errorState = UpdatePemasokErrorState(
            namaPemasok = if (event.namaPemasok.isNotEmpty()) null else "Nama Pemasok tidak boleh kosong",
            alamatPemasok = if (event.alamatPemasok.isNotEmpty()) null else "Alamat Pemasok tidak boleh kosong",
            teleponPemasok = if (event.teleponPemasok.isNotEmpty()) null else "Telepon Pemasok tidak boleh kosong"
        )
        pemasokuiState = pemasokuiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updatePemasok() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    val pemasok = pemasokuiState.updatePemasokUiEvent.toPemasok()
                    pmk.updatePemasok(pemasok.idPemasok, pemasok)
                    pemasokuiState = pemasokuiState.copy(isSuccess = true, snackBarMessage = "Data Pemasok Berhasil Diperbarui")
                    delay(3000)
                    resetSnackBarMessage()
                } catch (e: Exception) {
                    e.printStackTrace()
                    pemasokuiState = pemasokuiState.copy(isError = true, errorMessage = e.message)
                    delay(3000)
                    resetSnackBarMessage()
                }
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

data class UpdatePemasokUiState(
    val updatePemasokUiEvent: UpdatePemasokUiEvent = UpdatePemasokUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isEntryValid: UpdatePemasokErrorState = UpdatePemasokErrorState(),
    val snackBarMessage: String? = null
)

data class UpdatePemasokErrorState(
    val namaPemasok: String? = null,
    val alamatPemasok: String? = null,
    val teleponPemasok: String? = null
) {
    fun isValid(): Boolean {
        return namaPemasok == null && alamatPemasok == null && teleponPemasok == null
    }
}

data class UpdatePemasokUiEvent(
    val idPemasok: String = "",
    val namaPemasok: String = "",
    val alamatPemasok: String = "",
    val teleponPemasok: String = ""
)

fun UpdatePemasokUiEvent.toPemasok(): Pemasok = Pemasok(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)

fun Pemasok.toUpdatePemasokUiEvent(): UpdatePemasokUiEvent = UpdatePemasokUiEvent(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)