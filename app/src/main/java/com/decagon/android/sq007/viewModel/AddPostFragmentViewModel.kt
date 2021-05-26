package com.decagon.android.sq007.viewModel

import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.repository.Repository

class AddPostFragmentViewModel : ViewModel() {

    //private val postsRepository = Repository()

    fun addNewPost(userId: String, title: String, body: String) {
            Repository.createPost(userId, title, body)
    }
}