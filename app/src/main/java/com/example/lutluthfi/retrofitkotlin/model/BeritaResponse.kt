package com.example.lutluthfi.retrofitkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BeritaResponse {

    open class Beritas {
        @SerializedName("data")
        @Expose
        open val datas: List<Berita>? = null
    }

    open class Berita {
        @SerializedName("id")
        @Expose
        open val id: Int? = null
        @SerializedName("judul")
        @Expose
        open val judul: String? = null
        @SerializedName("foto")
        @Expose
        open val foto: String? = null
        @SerializedName("konten")
        @Expose
        open val konten: String? = null
        @SerializedName("sumber")
        @Expose
        open val sumber: String? = null
        @SerializedName("created_at")
        @Expose
        open val createdAt: String? = null
    }
}