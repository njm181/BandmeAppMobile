package com.bandme.bandmeappmobile.domain.di

import com.bandme.bandmeappmobile.data.service.LoginService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networLoginkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) } //el get() detecta que necesita pasarle una instancia de okHttpClient del arbol de dependencias por parametro
    single { provideApiService(get(), LoginService::class.java) }
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://bandme-login-api.herokuapp.com/api/v1/login/")//BuildConfig.UrlBase
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

fun provideApiService(retrofit: Retrofit, apiService: Class<LoginService>) =
    createService(retrofit, apiService)

fun <T> createService(retrofit: Retrofit, serviceClassGeneric: Class<T>): T = retrofit.create(serviceClassGeneric)