package com.example.userauthapp


import retrofit2.http.*

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("api/register")
    suspend fun register(@Body registerRequest: RegisterRequest): MessageResponse

    @POST("api/forgot-password")
    suspend fun forgotPassword(@Body map: Map<String, String>): MessageResponse

    @GET("api/users")
    suspend fun getUsers(@Header("Authorization") token: String): List<User>
}