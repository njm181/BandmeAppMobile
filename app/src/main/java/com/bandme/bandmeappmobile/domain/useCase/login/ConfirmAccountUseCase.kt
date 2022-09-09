package com.bandme.bandmeappmobile.domain.useCase.login

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl
import com.bandme.bandmeappmobile.domain.entity.ConfirmAccount

class ConfirmAccountUseCase(private val loginRepository: LoginRepositoryImpl) {

    suspend fun invoke(code: String): ConfirmAccount?{
        return loginRepository.confirmAccount(code)
    }
}