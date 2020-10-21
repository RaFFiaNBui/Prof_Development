package ru.samarin.prodev.model.datasource

import io.reactivex.Observable
import ru.samarin.prodev.model.data.DataModel

class RoomDataBaseImplementation: DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }
}