package com.bandme.bandmeappmobile.domain.repository

import com.bandme.bandmeappmobile.domain.entity.UserValidateLogin
import com.bandme.bandmeappmobile.domain.entity.ValidateCodeResetPassword
import com.bandme.bandmeappmobile.domain.entity.ValidateEmailResetPassword
import com.bandme.bandmeappmobile.domain.entity.ValidateResetPassword

interface LoginRepository {
    suspend fun validateEmail(email: String): Boolean?

    suspend fun validateLogin(email: String, password: String): UserValidateLogin?

    suspend fun validateEmailResetPassword(email: String): ValidateEmailResetPassword?

    suspend fun validateCodeResetPassword(code: String): ValidateCodeResetPassword?

    suspend fun validateResetPassword(newPassword: String, authorization: String): ValidateResetPassword?
}