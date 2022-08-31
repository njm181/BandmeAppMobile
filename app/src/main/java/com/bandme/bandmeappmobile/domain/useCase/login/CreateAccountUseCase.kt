package com.bandme.bandmeappmobile.domain.useCase.login

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl
import com.bandme.bandmeappmobile.domain.entity.CreateAccount

class CreateAccountUseCase(private val loginRepository: LoginRepositoryImpl) {

    suspend fun invoke(
        email: String,
        provider: String,
        password: String,
        userType: String
    ): CreateAccount?{
        return loginRepository.createAccount(
            email = email,
            provider = provider,
            userType = userType,
            password = password,
        )
    }
}