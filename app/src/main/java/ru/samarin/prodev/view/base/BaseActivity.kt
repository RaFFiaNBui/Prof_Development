package ru.samarin.prodev.view.base

import androidx.appcompat.app.AppCompatActivity
import ru.samarin.prodev.model.data.AppState
import ru.samarin.prodev.viewmodel.BaseViewModel
import ru.samarin.prodev.viewmodel.Interactor

abstract class BaseActivity<T : AppState, I : Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    abstract fun renderData(dataModel: T)
}