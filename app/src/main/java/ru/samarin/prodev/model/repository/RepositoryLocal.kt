package ru.samarin.prodev.model.repository

import ru.samarin.prodev.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
}