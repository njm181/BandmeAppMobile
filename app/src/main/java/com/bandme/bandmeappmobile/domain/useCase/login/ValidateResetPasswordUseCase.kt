package com.bandme.bandmeappmobile.domain.useCase.login

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl
import com.bandme.bandmeappmobile.domain.entity.ValidateResetPassword

class ValidateResetPasswordUseCase (private val loginRepository: LoginRepositoryImpl) {

    suspend fun invoke(newPassword: String, authorization: String): ValidateResetPassword? {
        return loginRepository.validateResetPassword(newPassword = newPassword, authorization = authorization)
    }
}