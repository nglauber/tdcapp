package br.com.nglauber.tdcapp.data.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TdcWebServiceFactory {

    fun makeTdcWebService(authStore: TdcAuthStore, isDebug: Boolean): TdcWebService {
        val okHttpClient = makeOkHttpClient(authStore, makeLoggingInterceptor(isDebug))
        return makeTdcWebService(okHttpClient)
    }

    private fun makeTdcWebService(okHttpClient: OkHttpClient): TdcWebService {
        val retrofit = Retrofit.Builder()
                .baseUrl(TdcWebService.API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(TdcWebService::class.java)
    }

    private fun makeOkHttpClient(tdcAuth: TdcAuthStore,
                                 httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(TdcAuthInterceptor(tdcAuth))
                .authenticator(TdcTokenAuthenticator(tdcAuth))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }
}