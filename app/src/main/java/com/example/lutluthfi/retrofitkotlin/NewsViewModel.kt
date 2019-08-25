package com.example.lutluthfi.retrofitkotlin

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.widget.Toast
import com.example.lutluthfi.retrofitkotlin.model.BeritaResponse
import com.example.lutluthfi.retrofitkotlin.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class NewsViewModel : ViewModel() {

    // Live Data
    val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val apiResponseSuccessLiveData: MutableLiveData<BeritaResponse.Beritas> = MutableLiveData()
    val errorMsgLiveData: MutableLiveData<String> = MutableLiveData()



    fun fetchingNewsByPage(page: Int) {
        isLoadingLiveData.value = true
        CompositeDisposable().add(ApiClient.create().getBeritaByPage(page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BeritaResponse.Beritas>() {
                    override fun onNext(t: BeritaResponse.Beritas) {
                        isLoadingLiveData.value = false
                        apiResponseSuccessLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        isLoadingLiveData.value = false
                        errorMsgLiveData.value = e.message
                        Log.d("MainActivity", e.message)
                    }

                    override fun onComplete() {
                        isLoadingLiveData.value = false
//                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                    }
                }))
    }
}