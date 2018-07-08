package br.com.nglauber.tdcapp.data.remote.service

import br.com.nglauber.tdcapp.BuildConfig
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class TdcTokenAuthenticator @Inject constructor(
        private val tdcAuth: TdcAuthStore
) : Authenticator {
    companion object {
        const val ACCESS_TOKEN_URL = "https://api.globalcode.com.br/v1/oauth2/token"
        const val CLIENT_ID = BuildConfig.API_CLIENT_ID
        const val SECRET = BuildConfig.API_SECRET

        const val AUTHORIZATION_HEADER = "Authorization"
        const val CONTENT_TYPE_HEADER = "Content-Type"
    }

    @Throws(IOException::class)
    override fun authenticate(route: Route, response: Response): Request? {
        if (getRetryCount(response) >= 3) {
            return null // If we've failed 3 times, give up.
        }

        val refreshedAccessToken = getNewAccessToken()
        if (refreshedAccessToken != null) {
            return response.request()
                    .newBuilder()
                    .header(AUTHORIZATION_HEADER, "Bearer $refreshedAccessToken")
                    .build()
        }
        return null
    }

    private fun getNewAccessToken(): String? {
        val okHttpClient = OkHttpClient.Builder()
                .build()
        val authRequest = Request.Builder()
                .url(ACCESS_TOKEN_URL)
                .addHeader(AUTHORIZATION_HEADER, Credentials.basic(CLIENT_ID, SECRET))
                .addHeader(CONTENT_TYPE_HEADER, "application/json")
                .build()
        val authResponse = okHttpClient.newCall(authRequest).execute()
        if (authResponse.code() == 200) {
            val authResponseBody = authResponse.body()?.string()
            val jsonObject = JSONObject(authResponseBody)
            val accessToken = jsonObject.getString("Access-Token")
            tdcAuth.saveAccessToken(accessToken)
            return accessToken
        }
        return null
    }

    private fun getRetryCount(response: Response?): Int {
        var tempResponse = response?.priorResponse()
        var result = 1

        while (tempResponse != null) {
            tempResponse = tempResponse.priorResponse()
            result++
        }
        return result
    }
}