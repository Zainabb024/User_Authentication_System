package com.example.userauthapp

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

data class LoginResponse(
    val token: String,
    val userId: String
)

data class MessageResponse(
    val message: String
)

data class User(
    val _id: String,
    val email: String,
    val name: String
)