package br.com.nglauber.tdcapp.repository.remote.mapper

interface Mapper<in R, out M> {
    fun parse(remote: R): M
}
