package com.example.a3_133.ui.viewmodel.merk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Merk
import com.example.a3_133.repository.MerkRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UpdateMerkViewModel(
    private val mrk: MerkRepository
) : ViewModel() {

    var uiState by mutableStateOf(UpdateUiState())
        private set

    fun updateMerkState(updateUiEvent: UpdateUiEvent) {
        uiState = uiState.copy(updateUiEvent = updateUiEvent)
    }

    fun getMerkById(idMerk: String) {
        viewModelScope.launch {
            try {
                val merk = mrk.getMerkById(idMerk)
                uiState = UpdateUiState(updateUiEvent = merk.toUpdateUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = UpdateUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    private fun validateFields(): Boolean {
        val event = uiState.updateUiEvent
        val errorState = UpdateMerkErrorState(
            namaMerk = if (event.namaMerk.isNotEmpty()) null else "Nama Merk tidak boleh kosong",
            deskripsiMerk = if (event.deskripsiMerk.isNotEmpty()) null else "Deskripsi Merk tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateMerk() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    val merk = uiState.updateUiEvent.toMerk()
                    mrk.updateMerk(merk.idMerk, merk)
                    uiState = uiState.copy(isSuccess = true, snackBarMessage = "Data Merk Berhasil Diperbarui")
                    delay(3000)
                    resetSnackBarMessage()
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiState = uiState.copy(isError = true, errorMessage = e.message)
                    delay(3000)
                    resetSnackBarMessage()
                }
            }
        } else {
            uiState = uiState.copy(snackBarMessage = "Input tidak valid. Periksa kembali data merk Anda.")
            viewModelScope.launch {
                delay(3000)
                resetSnackBarMessage()
            }
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class UpdateUiState(
    val updateUiEvent: UpdateUiEvent = UpdateUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isEntryValid: UpdateMerkErrorState = UpdateMerkErrorState(),
    val snackBarMessage: String? = null
)

data class UpdateMerkErrorState(
    val namaMerk: String? = null,
    val deskripsiMerk: String? = null
) {
    fun isValid(): Boolean {
        return namaMerk == null && deskripsiMerk == null
    }
}

data class UpdateUiEvent(
    val idMerk: String = "",
    val namaMerk: String = "",
    val deskripsiMerk: String = ""
)

fun UpdateUiEvent.toMerk(): Merk = Merk(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)

fun Merk.toUpdateUiEvent(): UpdateUiEvent = UpdateUiEvent(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)