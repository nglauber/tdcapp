package br.com.nglauber.tdcapp.presentation.mapper

interface Mapper<D, P> {
    fun fromDomain(domain: D): P
    fun toDomain(presentation: P): D
}
