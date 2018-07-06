package br.com.nglauber.tdcapp.repository.remote.model

import com.google.gson.annotations.SerializedName

data class TdcSpeaker (
        val id: Int,
        val member: TdcMember,
        @SerializedName("minibio")
        val miniBio: TdcMiniBio
)