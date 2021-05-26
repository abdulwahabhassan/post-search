package com.decagon.android.sq007.data.api

import com.decagon.android.sq007.data.model.Post
import retrofit2.http.GET

interface ApiService {

   @GET("posts")
   suspend fun getPosts(): List<Post>
}