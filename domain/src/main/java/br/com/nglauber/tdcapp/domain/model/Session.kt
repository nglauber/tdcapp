package br.com.nglauber.tdcapp.domain.model

data class Session(
        val id: Long,
        val slot: Int,
        val order: Int,
        val activated: Boolean,
        val title: String,
        val description: String,
        val type: Int,
        val time: String,
        val eventId: Long,
        val modalityId: Long,
        val bookmarked: Boolean
)