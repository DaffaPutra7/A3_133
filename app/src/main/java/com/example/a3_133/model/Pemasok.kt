package com.example.a3_133.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Pemasok(
    @SerialName("id_pemasok")
    val idPemasok: String,

    @SerialName("nama_pemasok")
    val namaPemasok: String,

    @SerialName("alamat_pemasok")
    val alamatPemasok: String,

    @SerialName("telepon_pemasok")
    val teleponPemasok: String
)