package com.bandme.bandmeappmobile.domain.useCase.login

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl
import com.bandme.bandmeappmobile.domain.entity.ValidateEmailResetPassword

class ValidateEmailResetPasswordUseCase (private val loginRepository: LoginRepositoryImpl) {

    suspend fun invoke(email: String): ValidateEmailResetPassword? {
        return loginRepository.validateEmailResetPassword(email)
    }
}