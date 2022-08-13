package com.bandme.bandmeappmobile.ui

import android.app.Application
import com.bandme.bandmeappmobile.domain.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(appModules)
        }
    }
}