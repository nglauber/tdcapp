package br.com.nglauber.tdcapp.presentation.model

data class MemberBinding(
        val id: Int,
        val name: String,
        val company: String?,
        val role: String?,
        val email: String?,
        val city: String?,
        val state: String?,
        val country: String?
)