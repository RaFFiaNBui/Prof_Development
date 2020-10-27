package ru.samarin.prodev.view.history

import ru.samarin.prodev.model.data.AppState
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.repository.Repository
import ru.samarin.prodev.model.repository.RepositoryLocal
import ru.samarin.prodev.viewmodel.Interactor

class HistoryInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}