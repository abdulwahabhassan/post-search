package com.decagon.android.sq007.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.model.Comment


class ListOfCommentsAdapter(private val items: List<Comment>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_comments_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment: Comment = items[position]

        (holder as ViewHolder).bind(items[position])

    }


    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: Comment) = with(itemView) {

            findViewById<TextView>(R.id.name).text = context.getString(R.string.name_text_comment) + comment.name?.
            split(" ")?.map{ it -> it.capitalize() }?.joinToString(" ")
            findViewById<TextView>(R.id.user_id).text = context.getString(R.string.comment_id_text).toUpperCase() + " " + comment.id.toString()
            findViewById<TextView>(R.id.email).text = context.getString(R.string.email_text) + " " + comment.email?.toLowerCase()
            findViewById<TextView>(R.id.body).text = context.getString(R.string.comment).toUpperCase() + " " + comment.body?.capitalize()
        }

    }

}
