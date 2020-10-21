package ru.samarin.prodev.model.datasource

import io.reactivex.Observable
import ru.samarin.prodev.model.data.DataModel

class DataSourceLocal(
    private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()
) : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)
}