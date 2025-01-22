package com.example.a3_133.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_133.model.Pemasok
import com.example.a3_133.repository.MerkRepository
import com.example.a3_133.repository.PemasokRepository
import kotlinx.coroutines.launch

class InsertPemasokViewModel(private val pemasok: PemasokRepository) : ViewModel() {

    var pemasokuiState by mutableStateOf(InsertPemasokUiState())
        private set

    fun updateInsertPemasokState(insertpemasokUiEvent: InsertPemasokUiEvent){
        pemasokuiState = InsertPemasokUiState(insertPemasokUiEvent = insertpemasokUiEvent)
    }

    suspend fun insertPemasok(){
        viewModelScope.launch {
            try {
                pemasok.insertPemasok(pemasokuiState.insertPemasokUiEvent.toPemasok())
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPemasokUiState(
    val insertPemasokUiEvent: InsertPemasokUiEvent = InsertPemasokUiEvent()
)

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