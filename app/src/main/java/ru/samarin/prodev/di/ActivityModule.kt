package ru.samarin.prodev.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.samarin.prodev.view.main.MainActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}