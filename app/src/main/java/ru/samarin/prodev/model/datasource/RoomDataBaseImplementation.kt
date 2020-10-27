package ru.samarin.prodev.model.datasource

import ru.samarin.prodev.model.data.DataModel

class RoomDataBaseImplementation : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        TODO("Not yet implemented")
    }
}