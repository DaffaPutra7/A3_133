package com.example.a3_133.repository

import com.example.a3_133.service.MerkService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val merkRepository: MerkRepository
}

class A3Container : AppContainer {
    private val baseUrl = "http://10.0.2.2/tugasAkhir/" // Localhost diganti ip kalau run di hp
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val merkService: MerkService by lazy { retrofit.create(MerkService::class.java) }
    override val merkRepository: MerkRepository by lazy { NetworkMerkRepository(merkService) }
}