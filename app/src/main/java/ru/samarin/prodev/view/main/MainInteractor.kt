package ru.samarin.prodev.view.main

import io.reactivex.Observable
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.data.SearchResult
import ru.samarin.prodev.model.repository.Repository
import ru.samarin.prodev.presenter.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<SearchResult>>,
    private val localRepository: Repository<List<SearchResult>>
) : Interactor<DataModel> {
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<DataModel> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { DataModel.Success(it) }
        } else {
            localRepository.getData(word).map { DataModel.Success(it) }
        }
    }
}