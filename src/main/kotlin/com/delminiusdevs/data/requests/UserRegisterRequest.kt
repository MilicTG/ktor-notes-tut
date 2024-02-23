package com.delminiusdevs.data.requests

data class UserRegisterRequest(
    val email: String,
    val username: String,
    val password: String,
)
