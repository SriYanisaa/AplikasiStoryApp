package com.example.aplikasistoryapp

data class LoginResponse (
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult
)

