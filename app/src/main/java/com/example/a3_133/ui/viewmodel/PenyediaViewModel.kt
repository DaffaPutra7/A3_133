package com.example.a3_133.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a3_133.A3Applications

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeMerkViewModel(aplikasiA3().container.merkRepository) }
        initializer { InsertMerkViewModel(aplikasiA3().container.merkRepository) }
        initializer { UpdateMerkViewModel(aplikasiA3().container.merkRepository) }

        initializer { HomePemasokViewModel(aplikasiA3().container.pemasokRepository) }
        initializer { InsertPemasokViewModel(aplikasiA3().container.pemasokRepository) }
        initializer { UpdatePemasokViewModel(aplikasiA3().container.pemasokRepository) }

        initializer { HomeKategoriViewModel(aplikasiA3().container.kategoriRepository) }
        initializer { InsertKategoriViewModel(aplikasiA3().container.kategoriRepository) }
        initializer { UpdateKategoriViewModel(aplikasiA3().container.kategoriRepository) }
    }
}

fun CreationExtras.aplikasiA3(): A3Applications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as A3Applications)