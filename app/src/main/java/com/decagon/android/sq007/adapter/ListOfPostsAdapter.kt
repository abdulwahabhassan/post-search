package com.decagon.android.sq007.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.R

class ListOfPostsAdapter (
    private val items: List<Post>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(post: Post, itemView: View)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_posts_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position], clickListener)
    }


    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post, listener: OnItemClickListener) = with(itemView) {
            findViewById<TextView>(R.id.user_id).text = "${context.getString(R.string.user_id_text)} ${post.userId}"
            findViewById<TextView>(R.id.id).text = "${context.getString(R.string.post_id_text)} ${post.id.toString()}"
            findViewById<TextView>(R.id.title).text = "${context.getString(R.string.title_text)} ${post.title?.
            split(" ")?.map{ it -> it.capitalize() }?.joinToString(" ")}"
            findViewById<TextView>(R.id.body).text = post.body?.capitalize()


            setOnClickListener {
                listener.onItemClick(post, it)
            }
        }

    }

}
