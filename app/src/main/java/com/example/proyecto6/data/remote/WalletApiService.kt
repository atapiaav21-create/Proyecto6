package com.example.proyecto6.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WalletApiService {

    @GET("users")
    suspend fun obtenerUsuarios(): List<ApiUser>

    @POST("users")
    suspend fun crearUsuario(
        @Body usuario: ApiUser
    ): ApiUser
}
