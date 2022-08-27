package com.bandme.bandmeappmobile.data.repositoryImpl

import com.bandme.bandmeappmobile.data.dto.login.request.*
import com.bandme.bandmeappmobile.data.service.LoginService
import com.bandme.bandmeappmobile.data.utils.*
import com.bandme.bandmeappmobile.domain.entity.*
import com.bandme.bandmeappmobile.domain.repository.LoginRepository

class LoginRepositoryImpl(private val loginService: LoginService): LoginRepository {

    override suspend fun validateEmail(email: String): Boolean? {
        var resp: Boolean? = try {
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

    override suspend fun validateLogin(email: String, password: String): UserValidateLogin? {
        var resp: UserValidateLogin? = try {
            val response = loginService.validateLogin(ValidateLoginRequest(email = email, password = password))
            if (response.isSuccessful){
                response.body()?.toDomainUserValidateLogin()
            }else{
                null
            }
        }catch (exception: Exception){
            println("Error al realizar la request: ${exception.message}")
            null
        }
        return resp
    }

    override suspend fun validateEmailResetPassword(email: String): ValidateEmailResetPassword? {
        var resp: ValidateEmailResetPassword? = try {
            val response = loginService.validateEmailResetPassword(ValidateEmailRequest(email = email))
            if (response.isSuccessful){
                response.body()?.toDomainValidateEmailResetPassword()
            }else{
                null
            }
        }catch (exception: Exception){
            println("Error al realizar la request: ${exception.message}")
            null
        }
        return resp
    }

    override suspend fun validateCodeResetPassword(code: String): ValidateCodeResetPassword? {
        var resp: ValidateCodeResetPassword? = try {
            val response = loginService.validateCodeResetPassword(ValidateCodeRequest(code = code))
            if (response.isSuccessful){
                response.body()?.toDomainValidateCodeResetPassword()
            }else{
                null
            }
        }catch (exception: Exception){
            println("Error al realizar la request: ${exception.message}")
            null
        }
        return resp
    }

    override suspend fun validateResetPassword(newPassword: String, authorization: String): ValidateResetPassword? {
        var resp: ValidateResetPassword? = try {
            val response = loginService.validateResetPassword(authorization = authorization, ValidateResetPasswordRequest(newPassword = newPassword))
            if (response.isSuccessful){
                response.body()?.toDomainValidateResetPassword()
            }else{
                null
            }
        }catch (exception: Exception){
            println("Error al realizar la request: ${exception.message}")
            null
        }
        return resp
    }

    override suspend fun validateLoginGoogle(accessToken: String): ValidateGoogle? {
        var resp: ValidateGoogle? = try {
            val response = loginService.validateLoginGoogle(accessToken = ValidateGoogleRequest(accessToken))
            if (response.isSuccessful){
                response.body()?.toDomainValidateGoogle()
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