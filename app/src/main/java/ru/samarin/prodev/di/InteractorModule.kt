package ru.samarin.prodev.di

import dagger.Module
import dagger.Provides
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.repository.Repository
import ru.samarin.prodev.view.main.MainInteractor
import javax.inject.Named

@Module
class InteractorModule {
    @Provides
    internal fun provideInteractor(
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>,
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}