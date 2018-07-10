package br.com.nglauber.tdcapp.data.remote.mapper

interface Mapper<in R, out M> {
    fun parse(remote: R): M
}
