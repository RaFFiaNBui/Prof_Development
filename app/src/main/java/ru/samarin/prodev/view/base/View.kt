package ru.samarin.prodev.view.base

import ru.samarin.prodev.model.data.DataModel

interface View {
    fun renderData(dataModel: DataModel)
}