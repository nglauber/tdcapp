package br.com.nglauber.tdcapp.repository.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TdcSession(
        val id: Int,
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
) : Parcelable