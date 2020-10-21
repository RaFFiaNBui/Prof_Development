package ru.samarin.prodev.view.main

import androidx.lifecycle.LiveData
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import ru.samarin.prodev.model.data.AppState
import ru.samarin.prodev.utils.parseSearchResult
import ru.samarin.prodev.viewmodel.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onComplete() {}

            override fun onNext(t: AppState) {
                appState = parseSearchResult(t)
                liveDataForViewToObserve.value = appState
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }
        }
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    private fun doOnSubscribe(): (Disposable) -> Unit = {
        liveDataForViewToObserve.value = AppState.Loading(null)
    }
}