package com.example.nyschools.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient  {

    private val baseUrl = "https://data.cityofnewyork.us/resource/"

    // Add valid api in prod to avoid throttling
    private val apiKey = ""

    private val okHttpClient: OkHttpClient = OkHttpClient()

    val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}