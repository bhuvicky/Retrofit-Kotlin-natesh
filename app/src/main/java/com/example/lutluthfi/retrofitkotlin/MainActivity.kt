package com.example.lutluthfi.retrofitkotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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

class MainActivity : AppCompatActivity(), NewsAdapter.Callback {

    private val mCompositeDisposable = CompositeDisposable()
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: NewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mRecyclerView = findViewById(R.id.rv_berita)
        setupRecyclerView()
        fetchingNewsByPage(1)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = layoutManager
        mAdapter = NewsAdapter(this)
        mRecyclerView?.adapter = mAdapter
    }

    override fun onNewsItemClick(berita: BeritaResponse.Berita) {
        Toast.makeText(this, berita.judul, Toast.LENGTH_SHORT).show()
    }

    private fun fetchingNewsByPage(page: Int) {
        mCompositeDisposable.add(ApiClient.create().getBeritaByPage(page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BeritaResponse.Beritas>() {
                    override fun onNext(t: Beritas) {
                        mAdapter?.addNews(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("MainActivity", e.message)
                    }

                    override fun onComplete() {
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                    }
                }))
    }

    // Second option if you want to separate the logic
    private fun getObserverBeritas(): DisposableObserver<Beritas> {
        return object : DisposableObserver<Beritas>() {
            override fun onNext(@NonNull beritas: Beritas) {
            }

            override fun onError(@NonNull e: Throwable) {
            }

            override fun onComplete() {
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
