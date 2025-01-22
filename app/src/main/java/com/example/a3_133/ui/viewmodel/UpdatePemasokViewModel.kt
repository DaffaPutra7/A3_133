package com.example.a3_133.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Pemasok
import com.example.a3_133.repository.PemasokRepository
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

    fun updatePemasok() {
        viewModelScope.launch {
            try {
                val pemasok = pemasokuiState.updatePemasokUiEvent.toPemasok()
                pmk.updatePemasok(pemasok.idPemasok, pemasok)
                pemasokuiState = pemasokuiState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                pemasokuiState = pemasokuiState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}

data class UpdatePemasokUiState(
    val updatePemasokUiEvent: UpdatePemasokUiEvent = UpdatePemasokUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

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