package com.example.proyecto6.data.remote

import retrofit2.http.GET

interface WalletApiService {

    @GET("users")
    suspend fun obtenerUsuarios(): List<ApiUser>
}
