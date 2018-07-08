package br.com.nglauber.tdcapp.data.remote.service

interface TdcAuthStore {

    fun saveAccessToken(accessToken: String)

    fun getAccessToken(): String
}