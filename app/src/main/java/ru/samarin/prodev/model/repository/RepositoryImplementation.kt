package ru.samarin.prodev.model.repository

import io.reactivex.Observable
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.datasource.DataSource


class RepositoryImplementation(
    private val dataSource: DataSource<List<DataModel>>
) : Repository<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
