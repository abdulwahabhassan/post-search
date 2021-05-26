package com.decagon.android.sq007.data.api

import com.decagon.android.sq007.data.model.Post

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
}