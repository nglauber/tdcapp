package br.com.nglauber.tdcapp.repository.remote.service

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class TdcAuthInterceptor(context: Context) : Interceptor {
    private val accessToken = TdcAuth(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original
                .newBuilder()
                .header("Authorization", "Bearer ${accessToken.getAccessToken()}")
                .build()
        return chain.proceed(request)
    }
}