package com.decagon.android.sq007.retrofit

import com.decagon.android.sq007.model.Comment
import com.decagon.android.sq007.model.Post
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderAPI {

    @GET("posts")
    fun getPosts() : Call<List<Post>>

    @GET("posts/{id}/comments")
    fun getPostComments(@Path("id") postId: Int) : Call<List<Comment>>

    @POST("posts/{id}/comments")
    fun addComment(@Body comment: Comment, @Path("id") postId: Int) : Call<Comment>

    @FormUrlEncoded
    @POST("posts")
    fun addPost(
        @Field("userId") userId: String,
        @Field("title") title: String,
        @Field("body") body: String
    ) : Call<Post>
}