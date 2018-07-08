package br.com.nglauber.tdcapp.presentation.mapper

interface Mapper<in D, out P> {
    fun parse(domain: D): P
}
