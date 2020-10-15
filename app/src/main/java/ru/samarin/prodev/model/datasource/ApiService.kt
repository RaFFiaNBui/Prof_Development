package ru.samarin.prodev.model.datasource

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.samarin.prodev.model.data.SearchResult

interface ApiService {
    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<SearchResult>>
}