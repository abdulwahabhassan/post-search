package com.decagon.android.sq007.data.model

data class Post(
    var id: Int? = null,
    var userId: Int,
    var title: String,
    var body: String
)