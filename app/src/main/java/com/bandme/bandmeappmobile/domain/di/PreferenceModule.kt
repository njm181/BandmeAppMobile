package com.bandme.bandmeappmobile.domain.di

import com.bandme.bandmeappmobile.domain.utils.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    single { AppPreferences(androidContext()) }
}