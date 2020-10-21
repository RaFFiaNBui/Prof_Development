package ru.samarin.prodev.view.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import ru.samarin.prodev.R
import ru.samarin.prodev.model.data.AppState
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.utils.isOnline
import ru.samarin.prodev.view.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val observer = Observer<AppState> { renderData(it) }
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = viewModelFactory.create(MainViewModel::class.java)
        model.subscribe().observe(this@MainActivity, observer)
        fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, SEARCH_TAG)
        }
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewSuccess()
                val data = appState.data
                if (data.isNullOrEmpty()) {
                    showAlertDialog("Fail", "No definitions found")
                } else {

                    recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerview.adapter = adapter
                    adapter.setData(data)

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

    companion object {
        private const val SEARCH_TAG = "43543"
    }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }
}