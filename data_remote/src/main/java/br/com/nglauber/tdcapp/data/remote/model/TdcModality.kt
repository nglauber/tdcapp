package br.com.nglauber.tdcapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TdcModality(
        val id: Long,
        @SerializedName("descricao")
        val description: String,
        val urlSite: String?,
        @SerializedName("ativo")
        val active: Boolean?,
        @SerializedName("posicaoNoEvento")
        val positionOnEvent: Int,
        val slogan: String?,
        @SerializedName("descricaoDetalhada")
        val detailedDescription: String?,
        @SerializedName("publicoAlvo")
        val targetAudience: String?,
        @SerializedName("topicos")
        val topics: String?,
        @SerializedName("prerequisitos")
        val prerequisites: String?,
        @SerializedName("warning")
        val warning: String?,
        @SerializedName("publicarNoSite")
        val publishOnSite: Boolean?,
        @SerializedName("data")
        val date: String
)