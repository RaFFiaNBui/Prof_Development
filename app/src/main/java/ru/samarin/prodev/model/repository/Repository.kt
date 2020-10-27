package ru.samarin.prodev.model.repository

interface Repository<T> {
    suspend fun getData(word: String): T
}