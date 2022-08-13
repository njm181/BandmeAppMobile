package com.bandme.bandmeappmobile.domain.di

import com.bandme.bandmeappmobile.domain.useCase.ValidateEmailUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { ValidateEmailUseCase(get()) }
}