package com.delminiusdevs.data

import com.delminiusdevs.data.model.User

interface NoteDAO {
    suspend fun checkPasswordForEmail(email: String, password: String): Boolean
    suspend fun checkIfEmailExist(email: String): Boolean
    suspend fun registerUser(user: User): Boolean
}