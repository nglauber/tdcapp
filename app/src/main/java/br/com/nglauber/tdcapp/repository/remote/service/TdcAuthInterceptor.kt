package br.com.nglauber.tdcapp.repository.remote.service

import okhttp3.Interceptor
import okhttp3.Response

class TdcAuthInterceptor(private val auth: TdcAuthStore) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original
                .newBuilder()
                .header("Authorization", "Bearer ${auth.getAccessToken()}")
                .build()
        return chain.proceed(request)
    }
}