package com.example.a3_133.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a3_133.A3Applications

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeMerkViewModel(aplikasiMahasiswa().container.merkRepository) }
        initializer { InsertMerkViewModel(aplikasiMahasiswa().container.merkRepository) }
        initializer { UpdateMerkViewModel(aplikasiMahasiswa().container.merkRepository) }
    }
}

fun CreationExtras.aplikasiMahasiswa(): A3Applications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as A3Applications)