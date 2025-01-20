package com.example.a3_133.service

import com.example.a3_133.model.Merk
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface MerkService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("getmerk.php")
    suspend fun getMerk(): List<Merk>

    @GET("get1merk.php")
    suspend fun getMerkById(@Query("id_merk") id_merk:String): Merk

    @POST("insertmerk.php")
    suspend fun insertMerk(@Body merk: Merk)

    @PUT("editmerk.php")
    suspend fun updateMerk(@Query("id_merk") id_merk: String, @Body merk: Merk)

    @DELETE("deletemerk.php")
    suspend fun deleteMerk(@Query("id_merk") id_merk: String) : retrofit2.Response<Void>
}