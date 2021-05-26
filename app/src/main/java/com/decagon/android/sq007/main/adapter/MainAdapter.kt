package com.decagon.android.sq007.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.data.model.Post


class MainAdapter (
    private val items: ArrayList<Post>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position])
    }


    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post) = with(itemView) {
            findViewById<TextView>(R.id.user_id).text = "${context.getString(R.string.user_id_text)} ${post.userId}"
            findViewById<TextView>(R.id.id).text = "${context.getString(R.string.post_id_text)} ${post.id.toString()}"
            findViewById<TextView>(R.id.title).text = "${context.getString(R.string.title_text)} ${post.title?.
            split(" ")?.map{ it -> it.capitalize() }?.joinToString(" ")}"
            findViewById<TextView>(R.id.body).text = post.body?.capitalize()

        }

    }

    fun addData(list: List<Post>) {
        items.addAll(list)
    }

}