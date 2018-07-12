package br.com.nglauber.tdcapp.domain.model

data class Speaker(
        val id: Long,
        val member: Member,
        val miniBio: MiniBio
)