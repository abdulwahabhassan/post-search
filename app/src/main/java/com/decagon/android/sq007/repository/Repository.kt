package com.decagon.android.sq007.repository

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decagon.android.sq007.model.Comment
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.retrofit.RetrofitClient
import com.decagon.android.sq007.retrofit.JsonPlaceHolderAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

object Repository {

        var POSTAG = "POSTS"
        var COMMENTAG = "COMMENTS"
        var NEWCOMMENTTAG = "NEWCOMMENT"
        var NEWPOSTTAG = "NEWPOST"

    val listOfPosts = MutableLiveData<List<Post>>()
    val newPost = MutableLiveData<Post>()
    var nonLiveDataPostListForNewPost: MutableList<Post>? = null

    var nonLiveDataPostsListForQuery: MutableList<Post>? = null

    val listOfComments = MutableLiveData<List<Comment>>()
    val newComment = MutableLiveData<Comment>()
    var nonLiveDataCommentsListForNewComment: MutableList<Comment>? = null


        private val retrofit: Retrofit = RetrofitClient.getInstance()
        private var jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI::class.java)



    fun getPosts() : LiveData<List<Post>> {


            jsonPlaceHolderAPI.getPosts()
                .enqueue(object : Callback<List<Post>> {
                    override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {

                        if (response.isSuccessful) {

                            listOfPosts.value = response.body()?.reversed()
                            nonLiveDataPostsListForQuery = response.body()?.reversed() as MutableList<Post>?
                            nonLiveDataPostListForNewPost = response.body()?.reversed() as MutableList<Post>

                        } else {

                            Log.i(POSTAG, "list Of Posts -> Unsuccessful" + response.errorBody())

                        }
                    }
                    override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                            Log.d(POSTAG, "List Of Posts: Error -> " + t.message)
                    }
                })

        return listOfPosts
    }

    fun getComments(postId: Int) : LiveData<List<Comment>> {

            jsonPlaceHolderAPI.getPostComments(postId)
                .enqueue(object : Callback<List<Comment>> {
                    override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {

                        if (response.isSuccessful) {

                            listOfComments.value = response.body()?.reversed()
                            nonLiveDataCommentsListForNewComment = response.body()?.reversed() as MutableList<Comment>?

                        } else {

                            Log.i(COMMENTAG, "list Of Comments -> Unsuccessful" + response.errorBody())

                        }
                    }
                    override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                            Log.d(COMMENTAG, "List Of Comments: Error -> " + t.message)
                    }

                })

        return listOfComments
    }

    fun createComment(commentBody: String, postId:Int): LiveData<Comment> {

       val commentAsBodyOfPostRequest = Comment(body = commentBody)

       jsonPlaceHolderAPI.addComment(commentAsBodyOfPostRequest, postId)
           .enqueue(object : Callback<Comment> {

               override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                   if (response.isSuccessful) {

                       newComment.value = response.body()
                       val sizeOfCommment = nonLiveDataCommentsListForNewComment?.get(
                           nonLiveDataCommentsListForNewComment!!.size - 1)?.id

                       if (sizeOfCommment != null) {
                           newComment.value?.id = nonLiveDataCommentsListForNewComment?.size?.plus(sizeOfCommment)!!
                       }
                       nonLiveDataCommentsListForNewComment?.add(0, newComment.value!!)
                       listOfComments.value = nonLiveDataCommentsListForNewComment

                   } else {

                       Log.i(NEWCOMMENTTAG, "New Comment -> Unsuccessful" + response.errorBody())

                   }
               }

               override fun onFailure(call: Call<Comment>, t: Throwable) {

                   Log.d(NEWCOMMENTTAG, "New Comment -> Error" + t.message)

               }

           })

       return newComment
   }

    fun createPost(userId: String, title: String, body: String): LiveData<List<Post>> {

        jsonPlaceHolderAPI.addPost(userId, title, body)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {

                    if(response.isSuccessful) {

                        newPost.value = response.body()
                       newPost.value?.id = nonLiveDataPostListForNewPost?.size?.plus(1)
                        nonLiveDataPostListForNewPost?.add(0, newPost.value!!)
                        listOfPosts.value = nonLiveDataPostListForNewPost

                    } else {

                        Log.d(NEWPOSTTAG, "New Post -> Unsuccessful" + response.errorBody())

                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {

                        Log.d(NEWPOSTTAG, "New Post -> Error" + t.message)

                }
            })

        return listOfPosts
    }

    fun findPost(postId: String?): LiveData<List<Post>> {

        val liveDataList = MutableLiveData<List<Post>>()

        when {
            postId == "" -> {

                liveDataList.value = nonLiveDataPostsListForQuery

            }
            postId?.trim()?.isDigitsOnly() == true -> {

                val filteredListOfPost = nonLiveDataPostsListForQuery?.
                filter { it.id?.toString() == postId.trim()}
                liveDataList.value = filteredListOfPost!!

            }
            else -> {

                val filteredListOfPost = nonLiveDataPostsListForQuery?.
                filter { it.title.startsWith("${postId?.trim()}", true) }
                liveDataList.value = filteredListOfPost!!

            }
        }

        return liveDataList
    }

}