package br.com.nglauber.tdcapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TdcSession(
        val id: Long,
        val slot: Int,
        @SerializedName("ordem")
        val order: Int,
        @SerializedName("publicar")
        val activated: Boolean,
        @SerializedName("titulo")
        val title: String,
        @SerializedName("descricao")
        val description: String,
        @SerializedName("tipo")
        val type: Int,
        @SerializedName("horario")
        val time: String
)