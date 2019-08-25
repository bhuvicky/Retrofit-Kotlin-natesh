package com.example.lutluthfi.retrofitkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BeritaResponse {

    open class Beritas {
        @SerializedName("employee")
        @Expose
        open val datas: List<Berita>? = null
    }

    open class Berita {

        @SerializedName("name")
        @Expose
        open val nameuser: String? = null
        @SerializedName("designation")
        @Expose
        open val designation: String? = null

    }
}