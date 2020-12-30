package com.example.android4a.injection

import android.content.Context
import androidx.room.Room
import com.example.android4a.data.local.AppDatabase
import com.example.android4a.data.local.DatabaseDao
import com.example.android4a.data.repository.UserRepository
import com.example.android4a.domain.usecase.ConfirmRegistrationUseCase
import com.example.android4a.domain.usecase.CreateAccountUseCase
import com.example.android4a.domain.usecase.GetAccountUseCase
import com.example.android4a.presentation.main.MainViewModel
import com.example.android4a.presentation.register.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// just declare it
val presentationModule = module {
    factory { MainViewModel(get()) }
    factory { RegisterViewModel(get(), get()) }
}

val domainModule = module{
    factory { CreateAccountUseCase(get()) }
    factory { GetAccountUseCase(get()) }
    factory { ConfirmRegistrationUseCase(get()) }
}

val dataModule = module{
    single { UserRepository(get()) }
    single {createDatabase(androidContext())}
}

fun createDatabase(context: Context): DatabaseDao {

    val appDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()
    return appDatabase.databaseDao()
}


