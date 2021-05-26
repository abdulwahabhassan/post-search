package com.decagon.android.sq007.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.model.Comment
import com.decagon.android.sq007.repository.Repository

class CommentsPageFragmentViewModel : ViewModel() {

    private val listOfComments = MediatorLiveData<List<Comment>>()


    fun getListOfComments(postId: Int): LiveData<List<Comment>> {
        getAllComments(postId)
        return listOfComments
    }

    private fun getAllComments(postId: Int) {
        listOfComments.addSource(Repository.getComments(postId)) {
            comments -> listOfComments.postValue(comments)
        }
    }

    fun addNewComment(commentBody: String, postId: Int) {
        Repository.createComment(commentBody, postId)
    }
}