package br.com.nglauber.tdcapp.repository.model

data class Speaker(
        val id: Int,
        val member: Member,
        val miniBio: MiniBio
)