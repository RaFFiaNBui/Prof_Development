package ru.samarin.prodev.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.samarin.prodev.di.application
import ru.samarin.prodev.di.historyScreen
import ru.samarin.prodev.di.mainScreen

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}