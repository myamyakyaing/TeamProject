package com.example.teamproject.network

import com.example.teamproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object HttpClientProvider {
    fun provideOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient().newBuilder()
            builder.readTimeout(10, TimeUnit.SECONDS)
            builder.connectTimeout(
                5
                , TimeUnit.SECONDS
            )

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }
            return builder.build()
        }
}