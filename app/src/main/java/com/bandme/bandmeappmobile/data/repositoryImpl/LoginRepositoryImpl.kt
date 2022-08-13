package com.bandme.bandmeappmobile.data.repositoryImpl

import com.bandme.bandmeappmobile.data.request.ValidateEmailRequest
import com.bandme.bandmeappmobile.data.service.LoginService
import com.bandme.bandmeappmobile.domain.repository.LoginRepository

class LoginRepositoryImpl(private val loginService: LoginService): LoginRepository {

    override suspend fun validateEmail(email: String): Boolean? {
        val response = loginService.validateEmail(ValidateEmailRequest(email))
        var resp: Boolean? = null

        resp = if (response.isSuccessful){
            response.body()?.exist_email == true
        }else{
            null
        }

        return resp
    }
}