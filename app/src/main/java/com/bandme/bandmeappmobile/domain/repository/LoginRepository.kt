package com.bandme.bandmeappmobile.domain.repository

interface LoginRepository {
    suspend fun validateEmail(email: String): Boolean?
}