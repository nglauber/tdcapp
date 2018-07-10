package br.com.nglauber.tdcapp.data.remote.service

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.*
import java.io.IOException

class TdcTokenAuthenticator (
        private val tdcAuth: TdcAuthStore
) : Authenticator {
    companion object {
        const val ACCESS_TOKEN_URL = "https://api.globalcode.com.br/v1/oauth2/token"
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
        throw IOException("Invalid token in TdcTokenAuthenticator")
    }

    private fun getNewAccessToken(): String? {
        val okHttpClient = OkHttpClient.Builder()
                .build()
        val authRequest = Request.Builder()
                .url(ACCESS_TOKEN_URL)
                .addHeader(AUTHORIZATION_HEADER, Credentials.basic(tdcAuth.getClientId(), tdcAuth.getClientSecret()))
                .addHeader(CONTENT_TYPE_HEADER, "application/json")
                .build()
        val authResponse = okHttpClient.newCall(authRequest).execute()
        if (authResponse.code() == 200) {
            val authResponseBody = authResponse.body()?.string()
            val response = Gson().fromJson(authResponseBody, AuthResponse::class.java)
            tdcAuth.saveAccessToken(response.accessToken)
            return response.accessToken
        }
        throw IOException("Failed to get new access token in TdcTokenAuthenticator")
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

    inner class AuthResponse(
            @SerializedName("Access-Token")
            var accessToken: String
    )
}