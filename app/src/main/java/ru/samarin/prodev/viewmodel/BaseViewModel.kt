package ru.samarin.prodev.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.samarin.prodev.model.data.AppState

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { coroutineContext, throwable -> handleError(throwable) }
    )

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun handleError(error: Throwable)

}