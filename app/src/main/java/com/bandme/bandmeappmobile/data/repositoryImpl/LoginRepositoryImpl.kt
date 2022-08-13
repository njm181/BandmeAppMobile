package com.bandme.bandmeappmobile.data.repositoryImpl

import com.bandme.bandmeappmobile.data.request.ValidateEmailRequest
import com.bandme.bandmeappmobile.data.service.LoginService
import com.bandme.bandmeappmobile.domain.repository.LoginRepository

class LoginRepositoryImpl(private val loginService: LoginService): LoginRepository {

    override suspend fun validateEmail(email: String): Boolean? {
        var resp: Boolean? = null
        resp = try {
            val response = loginService.validateEmail(ValidateEmailRequest(email))
            if (response.isSuccessful){
                response.body()?.exist_email == true
            }else{
                null
            }
        }catch (exception: Exception){
            println("Error al realizar la request: ${exception.message}")
            null
        }
        return resp
    }
}