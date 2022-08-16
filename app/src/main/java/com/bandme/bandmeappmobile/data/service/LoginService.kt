package com.bandme.bandmeappmobile.data.service

import com.bandme.bandmeappmobile.data.dto.login.request.ValidateEmailRequest
import com.bandme.bandmeappmobile.data.dto.login.request.ValidateLoginRequest
import com.bandme.bandmeappmobile.data.dto.login.response.ValidateEmailResponse
import com.bandme.bandmeappmobile.data.dto.login.response.ValidateLoginResponse
import retrofit2.Response
import retrofit2.http.Body
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

}