package com.example.a3_133.service

import com.example.a3_133.model.Merk
import com.example.a3_133.model.Pemasok
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PemasokService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("getpemasok.php")
    suspend fun getPemasok(): List<Pemasok>

    @GET("get1pemasok.php")
    suspend fun getPemasokById(@Query("id_pemasok") id_pemasok:String): Pemasok

    @POST("insertpemasok.php")
    suspend fun insertPemasok(@Body pemasok: Pemasok)

    @PUT("editpemasok.php")
    suspend fun updatePemasok(@Query("id_pemasok") id_pemasok: String, @Body pemasok: Pemasok)

    @DELETE("deletepemasok.php")
    suspend fun deletePemasok(@Query("id_pemasok") id_pemasok: String) : retrofit2.Response<Void>
}