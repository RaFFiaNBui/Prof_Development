package ru.samarin.prodev.presenter

import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.view.base.View

interface Presenter<T : DataModel, S : View> {
    fun attachView(view: S)
    fun detachView(view: S)
    fun getData(word: String, isOnline: Boolean)
}