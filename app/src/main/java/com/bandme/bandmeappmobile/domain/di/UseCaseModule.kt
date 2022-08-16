package com.bandme.bandmeappmobile.domain.di

import com.bandme.bandmeappmobile.domain.useCase.login.ValidateEmailUseCase
import com.bandme.bandmeappmobile.domain.useCase.login.ValidateLoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { ValidateEmailUseCase(get()) }
    factory { ValidateLoginUseCase(get()) }
}