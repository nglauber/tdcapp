package br.com.nglauber.tdcapp.domain.model

data class Speaker(
        val id: Int,
        val member: Member,
        val miniBio: MiniBio
)