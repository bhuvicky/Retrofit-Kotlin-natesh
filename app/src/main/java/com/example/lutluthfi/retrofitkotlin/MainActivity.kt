package com.example.lutluthfi.retrofitkotlin


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.lutluthfi.retrofitkotlin.model.BeritaResponse
import com.example.lutluthfi.retrofitkotlin.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import com.example.lutluthfi.retrofitkotlin.model.BeritaResponse.Beritas
import io.reactivex.annotations.NonNull
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NewsAdapter.Callback {

    private var mAdapter: NewsAdapter? = null
    private lateinit var newsViewModel: NewsViewModel
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        /*recycler view */
        setupRecyclerView()
        setObservers();

        /*api call */
        executeNewsByPage()
    }
    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_berita?.setHasFixedSize(true)
        rv_berita?.layoutManager = layoutManager
        mAdapter = NewsAdapter(this)
        rv_berita?.adapter = mAdapter
    }

    private fun setObservers() {
        newsViewModel.isLoadingLiveData.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                progressBar?.visibility = View.VISIBLE
            } else {
                progressBar?.visibility = View.GONE
            }
        })

        newsViewModel.apiResponseSuccessLiveData.observe(this, Observer(this::handleApiResponseSuccess))
        newsViewModel.errorMsgLiveData.observe(this, Observer(this::handleApiResponseError))
    }

    private fun executeNewsByPage() {
        newsViewModel.fetchingNewsByPage(1)
    }

    override fun onNewsItemClick(berita: BeritaResponse.Berita) {
        Toast.makeText(this, berita.nameuser, Toast.LENGTH_SHORT).show()
    }

    private fun handleApiResponseSuccess(result: BeritaResponse.Beritas?) {
        mAdapter?.addNews(result!!)
    }

    private fun handleApiResponseError(error: String?) {
        Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()

    }
}
