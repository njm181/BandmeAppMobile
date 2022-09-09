package com.bandme.bandmeappmobile.domain.di

import com.bandme.bandmeappmobile.domain.useCase.login.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { ValidateEmailUseCase(get()) }
    factory { ValidateLoginUseCase(get()) }
    factory { ValidateEmailResetPasswordUseCase(get()) }
    factory { ValidateCodeResetPasswordUseCase(get()) }
    factory { ValidateResetPasswordUseCase(get()) }
    factory { ValidateGoogleUseCase(get()) }
    factory { CreateAccountUseCase(get()) }
    factory { ConfirmAccountUseCase(get()) }
}