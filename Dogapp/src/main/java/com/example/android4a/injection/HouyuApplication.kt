package com.example.android4a.injection

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class android4alication : Application(){
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // Android context
            androidContext(this@android4alication)
            // modules
            modules(presentationModule, domainModule, dataModule)
        }
    }
}