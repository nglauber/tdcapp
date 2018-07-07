package br.com.nglauber.tdcapp.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Session(
        val id: Int,
        val slot: Int,
        val order: Int,
        val activated: Boolean,
        val title: String,
        val description: String,
        val type: Int,
        val time: String
) : Parcelable