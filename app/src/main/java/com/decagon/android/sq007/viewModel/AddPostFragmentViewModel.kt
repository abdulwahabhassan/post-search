package com.decagon.android.sq007.viewModel

import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.repository.Repository

class AddPostFragmentViewModel : ViewModel() {

    fun addNewPost(userId: String, title: String, body: String) {
            Repository.createPost(userId, title, body)
    }
}