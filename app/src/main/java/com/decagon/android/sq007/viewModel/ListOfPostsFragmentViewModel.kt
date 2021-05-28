package com.decagon.android.sq007.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.repository.Repository

class ListOfPostsFragmentViewModel : ViewModel (){

     private val listOfPosts = MediatorLiveData<List<Post>>()

    init {
        getAllPosts()
    }

    fun getListOfPosts(): LiveData<List<Post>> {
        return listOfPosts
    }


    fun getAllPosts(){
        listOfPosts.addSource(Repository.getPosts()) {
                posts -> listOfPosts.postValue(posts)
        }
    }

    fun searchPosts(postId: String?) {
            listOfPosts.addSource(Repository.findPost(postId)) { posts ->
                listOfPosts.postValue(posts)
            }
    }

}