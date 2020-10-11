package ru.samarin.prodev.model.datasource

import io.reactivex.Observable
import ru.samarin.prodev.model.data.SearchResult

class DataSourceLocal(
    private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()
) : DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> =
        remoteProvider.getData(word)
}