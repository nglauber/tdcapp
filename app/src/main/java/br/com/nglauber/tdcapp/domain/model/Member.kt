package br.com.nglauber.tdcapp.domain.model

data class Member(
        val id: Int,
        val name: String,
        val company: String?,
        val role: String?,
        val email: String?,
        val city: String?,
        val state: String?,
        val country: String?
)