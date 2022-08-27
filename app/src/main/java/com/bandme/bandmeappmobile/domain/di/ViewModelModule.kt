package com.bandme.bandmeappmobile.domain.di

import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get(), get(), get(), get(), get(), get()) }
}