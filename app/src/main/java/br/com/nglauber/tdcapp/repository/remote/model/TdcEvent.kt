package br.com.nglauber.tdcapp.repository.remote.model

import com.google.gson.annotations.SerializedName

data class TdcEvent(
        val id: Int,
        @SerializedName("descricao")
        val description: String,
        @SerializedName("chave")
        val key: String?,
        @SerializedName("ativo")
        val active: Boolean?,
        @SerializedName("gratuito")
        val free: Boolean?,
        @SerializedName("dias")
        val days: Int,
        val online: Boolean?,
        @SerializedName("dataInicio")
        val startDate: String?,
        @SerializedName("dataTermino")
        val endDate: String?
)