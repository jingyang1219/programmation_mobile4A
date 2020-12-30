package com.example.houyuapp.injection

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HouyuApplication : Application(){
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // Android context
            androidContext(this@HouyuApplication)
            // modules
            modules(presentationModule, domainModule, dataModule)
        }
    }
}