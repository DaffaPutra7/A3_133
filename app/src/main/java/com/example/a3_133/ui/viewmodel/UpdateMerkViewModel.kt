package com.example.a3_133.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Merk
import com.example.a3_133.repository.MerkRepository
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

    fun updateMerk() {
        viewModelScope.launch {
            try {
                val merk = uiState.updateUiEvent.toMerk()
                mrk.updateMerk(merk.idMerk, merk)
                uiState = uiState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}

data class UpdateUiState(
    val updateUiEvent: UpdateUiEvent = UpdateUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

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