package br.com.nglauber.tdcapp.repository.remote.service

import android.content.Context
import android.preference.PreferenceManager

class TdcAuthStore(ctx: Context) {
    private val context = ctx.applicationContext

    fun saveAccessToken(accessToken: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(ACCESS_TOKEN_KEY, accessToken)
                .apply()
    }

    fun getAccessToken(): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(ACCESS_TOKEN_KEY, "")
    }

    companion object {
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}