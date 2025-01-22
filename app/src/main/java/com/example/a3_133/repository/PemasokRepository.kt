package com.example.a3_133.repository

import com.example.a3_133.model.Pemasok
import com.example.a3_133.service.PemasokService
import okio.IOException

interface PemasokRepository {
    suspend fun getPemasok(): List<Pemasok>
    suspend fun getPemasokById(idpemasok : String) : Pemasok
    suspend fun insertPemasok(pemasok: Pemasok)
    suspend fun updatePemasok(idpemasok: String, pemasok: Pemasok)
    suspend fun deletePemasok(idpemasok: String)
}

class NetworkPemasokRepository( private val pemasokApiService: PemasokService) : PemasokRepository {
    override suspend fun insertPemasok(pemasok: Pemasok) {
        pemasokApiService.insertPemasok(pemasok)
    }

    override suspend fun updatePemasok(idpemasok: String, pemasok: Pemasok) {
        pemasokApiService.updatePemasok(idpemasok, pemasok)
    }

    override suspend fun deletePemasok(idpemasok: String) {
        try {
            val response = pemasokApiService.deletePemasok(idpemasok)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pemasok. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPemasok(): List<Pemasok> = pemasokApiService.getPemasok()

    override suspend fun getPemasokById(idpemasok: String): Pemasok {
        return pemasokApiService.getPemasokById(idpemasok)
    }
}