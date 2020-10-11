package ru.samarin.prodev.model.datasource

import io.reactivex.Observable
import ru.samarin.prodev.model.data.SearchResult

class DataSourceRemote(
    private val remoteProvider: RetrofitImplementation = RetrofitImplementation()
) : DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> =
        remoteProvider.getData(word)
}