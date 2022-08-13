package com.bandme.bandmeappmobile.domain.di

import com.bandme.bandmeappmobile.data.repositoryImpl.LoginRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { LoginRepositoryImpl(get()) }
}