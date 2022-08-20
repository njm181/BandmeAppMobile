package com.bandme.bandmeappmobile.data.service

import com.bandme.bandmeappmobile.data.dto.login.request.ValidateCodeRequest
import com.bandme.bandmeappmobile.data.dto.login.request.ValidateEmailRequest
import com.bandme.bandmeappmobile.data.dto.login.request.ValidateLoginRequest
import com.bandme.bandmeappmobile.data.dto.login.request.ValidateResetPasswordRequest
import com.bandme.bandmeappmobile.data.dto.login.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST("validate/email")
    suspend fun validateEmail(
        @Body email: ValidateEmailRequest
    ) : Response<ValidateEmailResponse>

    @POST("validate/login")
    suspend fun validateLogin(
        @Body validateLoginRequest: ValidateLoginRequest
    ) : Response<ValidateLoginResponse>

    @POST("validate/email-reset")
    suspend fun validateEmailResetPassword(
        @Body email: ValidateEmailRequest
    ) : Response<ValidateEmailResetPasswordResponse>

    @POST("validate/email-code")
    suspend fun validateCodeResetPassword(
        @Body code: ValidateCodeRequest
    ) : Response<ValidateCodeResetPasswordResponse>

    @POST("validate/reset-password")
    suspend fun validateResetPassword(
        @Header("auth-token") authorization: String,
        @Body newPassword: ValidateResetPasswordRequest
    ) : Response<ValidateResetPasswordResponse>
}