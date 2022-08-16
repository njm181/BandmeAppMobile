package com.bandme.bandmeappmobile.domain.useCase.login

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl

class ValidateEmailUseCase(private val loginRepository: LoginRepositoryImpl) {

    suspend fun invoke(email: String): Boolean? {
        return loginRepository.validateEmail(email)
    }
}