package br.com.nglauber.tdcapp.repository.model

data class Event(
        val id: Int,
        val description: String,
        val key: String?,
        val active: Boolean?,
        val free: Boolean?,
        val days: Int,
        val online: Boolean?,
        val startDate: String?,
        val endDate: String?
)