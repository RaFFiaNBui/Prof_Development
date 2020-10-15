package ru.samarin.prodev.view.main

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.datasource.DataSourceLocal
import ru.samarin.prodev.model.datasource.DataSourceRemote
import ru.samarin.prodev.model.repository.RepositoryImplementation
import ru.samarin.prodev.presenter.Presenter
import ru.samarin.prodev.rx.SchedulerProvider
import ru.samarin.prodev.view.base.View

class MainPresenterImpl<T : DataModel, V : View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView == null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
    }

    private fun doOnSubscribe(): (Disposable) -> Unit = {
        currentView?.renderData(DataModel.Loading(null))
    }

    private fun getObserver(): DisposableObserver<DataModel> {
        return object : DisposableObserver<DataModel>() {
            override fun onComplete() {}

            override fun onNext(t: DataModel) {
                currentView?.renderData(t)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(DataModel.Error(e))
            }
        }
    }
}