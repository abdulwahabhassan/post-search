package com.decagon.android.sq007.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var INSTANCE: Retrofit? = null

    fun getInstance():Retrofit {
        if (INSTANCE == null) {
            INSTANCE = Retrofit.Builder ()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    return INSTANCE!!
    }
}