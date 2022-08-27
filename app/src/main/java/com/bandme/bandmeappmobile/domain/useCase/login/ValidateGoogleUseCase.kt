package com.bandme.bandmeappmobile.domain.useCase.login

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl
import com.bandme.bandmeappmobile.domain.entity.ValidateGoogle

class ValidateGoogleUseCase(private val loginRepository: LoginRepositoryImpl) {
    suspend fun invoke(accessToken: String): ValidateGoogle? {
        return loginRepository.validateLoginGoogle(accessToken = accessToken)
    }
}