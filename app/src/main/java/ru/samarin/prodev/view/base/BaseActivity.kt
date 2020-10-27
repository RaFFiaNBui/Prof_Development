package ru.samarin.prodev.view.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.samarin.prodev.R
import ru.samarin.prodev.model.data.AppState
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.utils.AlertDialogFragment
import ru.samarin.prodev.utils.isOnline
import ru.samarin.prodev.viewmodel.BaseViewModel
import ru.samarin.prodev.viewmodel.Interactor

abstract class BaseActivity<T : AppState, I : Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    abstract fun setDataToAdapter(data: List<DataModel>)

    protected fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewSuccess()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog("Fail", "No definitions found")
                    } else {
                        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.load != null) {
                    progressbar_horizontal.visibility = android.view.View.VISIBLE
                    progressbar_circular.visibility = android.view.View.GONE
                    progressbar_horizontal.progress = appState.load
                } else {
                    progressbar_horizontal.visibility = android.view.View.GONE
                    progressbar_circular.visibility = android.view.View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewSuccess()
                showAlertDialog("Error", appState.error.message)
            }
        }
    }

    private fun showViewSuccess() {
        success_layout.visibility = android.view.View.VISIBLE
        loading_layout.visibility = android.view.View.GONE
    }

    private fun showViewLoading() {
        success_layout.visibility = android.view.View.GONE
        loading_layout.visibility = android.view.View.VISIBLE
    }


    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onPostResume() {
        super.onPostResume()
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_offline),
            getString(R.string.dialog_message_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "dialog tag"
    }
}