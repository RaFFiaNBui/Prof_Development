package ru.samarin.prodev.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.samarin.prodev.R
import ru.samarin.prodev.model.data.DataModel
import ru.samarin.prodev.model.data.SearchResult
import ru.samarin.prodev.presenter.Presenter
import ru.samarin.prodev.view.base.BaseActivity
import ru.samarin.prodev.view.base.View

class MainActivity : BaseActivity<DataModel>() {

    private val adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener{
            override fun onItemClick(data: SearchResult) {
                Toast.makeText(this@MainActivity,data.text,Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord,true)
                }
            })
            searchDialogFragment.show(supportFragmentManager,SEARCH_TAG)
        }
    }

    override fun createPresenter(): Presenter<DataModel, View> {
        return MainPresenterImpl()
    }

    override fun renderData(dataModel: DataModel) {
        when (dataModel) {
            is DataModel.Success -> {
                val searchResult = dataModel.data
                if (searchResult == null || searchResult.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        recyclerview.adapter = MainAdapter(onListItemClickListener, searchResult)
                    } else {
                        adapter!!.setData(searchResult)
                    }
                }
            }
            is DataModel.Loading -> {
                showViewLoading()
                if (dataModel.progress != null) {
                    progressbar_horizontal.visibility = android.view.View.VISIBLE
                    progressbar_circular.visibility = android.view.View.GONE
                    progressbar_horizontal.progress = dataModel.progress
                } else {
                    progressbar_horizontal.visibility = android.view.View.GONE
                    progressbar_circular.visibility = android.view.View.VISIBLE
                }
            }
            is DataModel.Error -> {
                showErrorScreen(dataModel.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.unknown_error)
        reload_button.setOnClickListener {
            presenter.getData("hi", true)
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