package br.com.nglauber.tdcapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
        @PrimaryKey(autoGenerate = false)
        val id: Long,
        val slot: Int,
        val order: Int,
        val activated: Boolean,
        val title: String,
        val description: String,
        val type: Int,
        val time: String,
        // I'm not saving the event and modality information into the database,
        // it's just to filter the sessions by event
        val eventId: Long,
        val modalityId: Long
)