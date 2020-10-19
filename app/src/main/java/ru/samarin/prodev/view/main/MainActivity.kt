package ru.samarin.prodev.view.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.samarin.prodev.R
import ru.samarin.prodev.model.data.AppState
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.view.base.BaseActivity

class MainActivity : BaseActivity<AppState,MainInteractor>() {

    private val observer = Observer<AppState> {renderData(it)}
    private val adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener{
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity,data.text,Toast.LENGTH_SHORT).show()
            }
        }

    override val model: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord,true).observe(this@MainActivity,observer)
                }
            })
            searchDialogFragment.show(supportFragmentManager,SEARCH_TAG)
        }
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val data = appState.data
                if (data == null || data.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        recyclerview.adapter = MainAdapter(onListItemClickListener, data)
                    } else {
                        adapter!!.setData(data)
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
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.unknown_error)
        reload_button.setOnClickListener {
            model.getData("hi", true).observe(this,observer)
        }
    }

    private fun showViewSuccess() {
        success_layout.visibility = android.view.View.VISIBLE
        loading_layout.visibility = android.view.View.GONE
        error_layout.visibility = android.view.View.GONE
    }

    private fun showViewLoading() {
        success_layout.visibility = android.view.View.GONE
        loading_layout.visibility = android.view.View.VISIBLE
        error_layout.visibility = android.view.View.GONE
    }

    private fun showViewError() {
        success_layout.visibility = android.view.View.GONE
        loading_layout.visibility = android.view.View.GONE
        error_layout.visibility = android.view.View.VISIBLE
    }

    companion object {
        private const val SEARCH_TAG = "43543"
    }
}