package ru.samarin.prodev.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.datasource.RetrofitImplementation
import ru.samarin.prodev.model.datasource.RoomDataBaseImplementation
import ru.samarin.prodev.model.repository.Repository
import ru.samarin.prodev.model.repository.RepositoryImplementation
import ru.samarin.prodev.view.main.MainInteractor
import ru.samarin.prodev.view.main.MainViewModel

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(RoomDataBaseImplementation())
    }
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}