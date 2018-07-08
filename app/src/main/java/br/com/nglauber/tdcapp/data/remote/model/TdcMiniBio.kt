package br.com.nglauber.tdcapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TdcMiniBio(
        val id: Int,
        @SerializedName("chave")
        val key: String?,
        @SerializedName("texto")
        val text: String?,
        @SerializedName("urlFoto")
        val urlPhoto: String?,
        val urlTwitter: String?,
        @SerializedName("urlBlog1")
        val urlBlog: String?,
        val urlLinkedin: String?,
        val urlSite: String?,
        val urlGlobalcoders: String?
)