package ru.samarin.prodev.view.main

import io.reactivex.Observable
import ru.samarin.prodev.model.data.AppState
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.repository.Repository
import ru.samarin.prodev.viewmodel.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}