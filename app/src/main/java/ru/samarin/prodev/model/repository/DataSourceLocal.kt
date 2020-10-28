package ru.samarin.prodev.model.repository

import ru.samarin.prodev.model.data.AppState
import ru.samarin.prodev.model.datasource.DataSource

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}