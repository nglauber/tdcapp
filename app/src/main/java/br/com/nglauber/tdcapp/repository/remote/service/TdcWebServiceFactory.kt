package br.com.nglauber.tdcapp.repository.remote.service

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TdcWebServiceFactory {

    fun makeTdWebService(context: Context, isDebug: Boolean): TdcWebService {
        val okHttpClient = makeOkHttpClient(context, makeLoggingInterceptor(isDebug))
        return makeAlbumWebService(okHttpClient)
    }

    private fun makeAlbumWebService(okHttpClient: OkHttpClient): TdcWebService {
        val retrofit = Retrofit.Builder()
                .baseUrl(TdcWebService.API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(TdcWebService::class.java)
    }

    private fun makeOkHttpClient(context: Context, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(TdcAuthInterceptor(context))
                .authenticator(TdcTokenAuthenticator(TdcAuth(context)))
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