package ru.samarin.prodev.model.datasource

import io.reactivex.Observable
import ru.samarin.prodev.model.data.SearchResult

class RoomDataBaseImplementation: DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> {
        TODO("Not yet implemented")
    }
}