package com.example.lutluthfi.retrofitkotlin.network

import com.example.lutluthfi.retrofitkotlin.model.BeritaResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {

    @GET("json_object.json")
    fun getBeritaByPage(@Query("page") page : Int) : Observable<BeritaResponse.Beritas>
}