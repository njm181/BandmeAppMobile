package com.bandme.bandmeappmobile.domain.repository

import com.bandme.bandmeappmobile.domain.entity.*

interface LoginRepository {
    suspend fun validateEmail(email: String): Boolean?

    suspend fun validateLogin(email: String, password: String): UserValidateLogin?

    suspend fun validateEmailResetPassword(email: String): ValidateEmailResetPassword?

    suspend fun validateCodeResetPassword(code: String): ValidateCodeResetPassword?

    suspend fun validateResetPassword(newPassword: String, authorization: String): ValidateResetPassword?

    suspend fun validateLoginGoogle(accessToken: String): ValidateGoogle?

    suspend fun createAccount(
        email: String,
        provider: String,
        password: String = "",
        userType: String
    ) : CreateAccount?
}