package br.com.nglauber.tdcapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TdcMember (
        val id: Int,
        @SerializedName("nome")
        val name: String,
        @SerializedName("empresa")
        val company: String?,
        @SerializedName("funcao")
        val role: String?,
        @SerializedName("email1")
        val email: String,
        @SerializedName("cidade")
        val city: String?,
        @SerializedName("estado")
        val state: String?,
        @SerializedName("pais")
        val country: String?
)