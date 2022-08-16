package com.bandme.bandmeappmobile.domain.useCase.login

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl
import com.bandme.bandmeappmobile.domain.entity.UserValidateLogin

class ValidateLoginUseCase (private val loginRepository: LoginRepositoryImpl) {

    suspend fun invoke(email: String, password: String): UserValidateLogin? {
        return loginRepository.validateLogin(email = email, password = password)
    }
}