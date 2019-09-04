package com.example.teamproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestAdapter {
//        const val URL_BASE = "http://10.10.21.97:8080/talent/api/"
    const val URL_BASE = "http://10.10.21.61:8080/TalentManagementSystem/api/"
//    const val URL_BASE = "http://10.10.21.80:8087/TalentManagementSystem/api/"
    private var retrofit: Retrofit? = null
    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientProvider.provideOkHttpClient())
                .build()
        }
        return retrofit!!
    }
}