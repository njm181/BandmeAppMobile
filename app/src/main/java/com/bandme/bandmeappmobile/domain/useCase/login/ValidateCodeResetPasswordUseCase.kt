package com.bandme.bandmeappmobile.domain.useCase.login

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl
import com.bandme.bandmeappmobile.domain.entity.ValidateCodeResetPassword
import com.bandme.bandmeappmobile.domain.entity.ValidateEmailResetPassword

class ValidateCodeResetPasswordUseCase (private val loginRepository: LoginRepositoryImpl) {

    suspend fun invoke(code: String): ValidateCodeResetPassword? {
        return loginRepository.validateCodeResetPassword(code)
    }
}