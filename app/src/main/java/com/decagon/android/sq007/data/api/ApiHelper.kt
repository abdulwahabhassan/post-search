package com.decagon.android.sq007.data.api

import com.decagon.android.sq007.data.model.Post

interface ApiHelper {

    suspend fun getPosts(): List<Post>

}