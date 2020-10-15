package ru.samarin.prodev.model.repository

import io.reactivex.Observable
import ru.samarin.prodev.model.data.SearchResult
import ru.samarin.prodev.model.datasource.DataSource


class RepositoryImplementation(
    private val dataSource: DataSource<List<SearchResult>>
) : Repository<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> {
        return dataSource.getData(word)
    }
}
