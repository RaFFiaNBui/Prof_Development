package ru.samarin.prodev.di

import androidx.room.Room
import org.koin.dsl.module
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.datasource.RetrofitImplementation
import ru.samarin.prodev.model.datasource.RoomDataBaseImplementation
import ru.samarin.prodev.model.repository.Repository
import ru.samarin.prodev.model.repository.RepositoryImplementation
import ru.samarin.prodev.model.repository.RepositoryImplementationLocal
import ru.samarin.prodev.model.repository.RepositoryLocal
import ru.samarin.prodev.room.HistoryDatabase
import ru.samarin.prodev.view.history.HistoryInteractor
import ru.samarin.prodev.view.history.HistoryViewModel
import ru.samarin.prodev.view.main.MainInteractor
import ru.samarin.prodev.view.main.MainViewModel

val application = module {
    single {
        Room.databaseBuilder(
            get(),
            HistoryDatabase::class.java,
            "HistoryDB"
        ).build()
    }

    single { get<HistoryDatabase>().historyDao() }

    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
    single<Repository<List<DataModel>>> {
        RepositoryImplementation(RetrofitImplementation())
    }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryInteractor(get(), get()) }
    factory { HistoryViewModel(get()) }
}
