package ru.samarin.prodev.model.datasource

import io.reactivex.Observable
import ru.samarin.prodev.model.data.DataModel

class DataSourceRemote(
    private val remoteProvider: RetrofitImplementation = RetrofitImplementation()
) : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)
}