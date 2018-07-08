package br.com.nglauber.tdcapp.ui.auth

import android.content.Context
import android.preference.PreferenceManager
import br.com.nglauber.tdcapp.data.remote.service.TdcAuthStore
import javax.inject.Inject

// TODO I'm accessing the remote layer. I'm not sure if it's correct
class TdcAuthStoreImpl @Inject constructor(ctx: Context): TdcAuthStore {
    private val context = ctx.applicationContext

    override fun saveAccessToken(accessToken: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(ACCESS_TOKEN_KEY, accessToken)
                .apply()
    }

    override fun getAccessToken(): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(ACCESS_TOKEN_KEY, "")
    }

    companion object {
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}