package ru.samarin.prodev.model.repository

import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.datasource.DataSource


class RepositoryImplementation(
    private val dataSource: DataSource<List<DataModel>>
) : Repository<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
