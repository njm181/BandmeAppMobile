package com.bandme.bandmeappmobile.domain.repository

import com.bandme.bandmeappmobile.domain.entity.UserValidateLogin

interface LoginRepository {
    suspend fun validateEmail(email: String): Boolean?

    suspend fun validateLogin(email: String, password: String): UserValidateLogin?
}