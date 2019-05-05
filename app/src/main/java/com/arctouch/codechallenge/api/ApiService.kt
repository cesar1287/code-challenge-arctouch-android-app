package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.util.URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    companion object {
        fun getTmdbApiClient() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}