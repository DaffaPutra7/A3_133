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

class InsertMerkViewModel(private val merk: MerkRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMerkState(insertUiEvent: InsertUiEvent){
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }

    private fun validateFields(): Boolean {
        val event = uiState.insertUiEvent
        val errorState = MerkErrorState(
            namaMerk = if (event.namaMerk.isNotEmpty()) null else "Nama Merk tidak boleh kosong",
            deskripsiMerk = if (event.deskripsiMerk.isNotEmpty()) null else "Deskripsi Merk tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertMerk() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    merk.insertMerk(uiState.insertUiEvent.toMerk())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Merk Berhasil Disimpan",
                        insertUiEvent = InsertUiEvent(),
                        isEntryValid = MerkErrorState()
                    )
                    delay(3000)
                    resetSnackBarMessage()
                } catch (e: Exception) {
                    uiState = uiState.copy(snackBarMessage = "Data Merk Gagal Disimpan")
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

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isEntryValid: MerkErrorState = MerkErrorState(),
    val snackBarMessage: String? = null
)

data class MerkErrorState(
    val namaMerk: String? = null,
    val deskripsiMerk: String? = null
) {
    fun isValid(): Boolean {
        return namaMerk == null && deskripsiMerk == null
    }
}

data class InsertUiEvent(
    val idMerk: String = "",
    val namaMerk: String = "",
    val deskripsiMerk: String = "",
)

fun InsertUiEvent.toMerk(): Merk = Merk(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)

fun Merk.toUiStateMerk(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Merk.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)