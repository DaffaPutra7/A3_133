package com.example.a3_133.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Merk(
    @SerialName("id_merk")
    val idMerk: String,

    @SerialName("nama_merk")
    val namaMerk: String,

    @SerialName("deskripsi_merk")
    val deskripsiMerk: String
)
