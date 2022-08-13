package com.bandme.bandmeappmobile.data.service

import com.bandme.bandmeappmobile.data.request.ValidateEmailRequest
import com.bandme.bandmeappmobile.data.response.ValidateEmailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("validate/email")
    suspend fun validateEmail(
        @Body email: ValidateEmailRequest
    ) : Response<ValidateEmailResponse>

}