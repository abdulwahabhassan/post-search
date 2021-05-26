package com.decagon.android.sq007.main.viewstate

import com.decagon.android.sq007.data.model.Post

sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    data class Posts(val post: List<Post>) : MainState()
    data class Error(val error: String?) : MainState()

}