package ru.samarin.prodev.model.datasource

interface DataSource<T> {
    suspend fun getData(word: String): T
}