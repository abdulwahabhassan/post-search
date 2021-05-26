package com.decagon.android.sq007.data.repository

import com.decagon.android.sq007.data.api.ApiHelper


class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getPosts() = apiHelper.getPosts()

}