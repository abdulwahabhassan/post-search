package com.decagon.android.sq007.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.adapter.ListOfCommentsAdapter
import com.decagon.android.sq007.databinding.FragmentPostPageBinding
import com.decagon.android.sq007.model.Comment
import com.decagon.android.sq007.viewModel.CommentsPageFragmentViewModel


class CommentsPageFragment : Fragment() {

    private var binding: FragmentPostPageBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var sendButton: Button
    private lateinit var commentEditText: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPostPageBinding.inflate(inflater, container, false)

        recyclerView = binding?.commentsRecyclerView as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        sendButton = binding?.button as Button
        commentEditText = binding?.commentEditText as EditText

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postId = arguments?.getInt(getString(R.string.post_id))
        val postTitle = arguments?.getString(getString(R.string.post_title))
        val postBody = arguments?.getString(getString(R.string.post_body))

        binding?.title?.text = postTitle?.split(" ")?.map{ it -> it.capitalize() }?.joinToString(" ")
        binding?.post?.text = postBody?.capitalize()
        binding?.postId?.text = "Post ID: ${postId?.toString()}"

        val viewModel: CommentsPageFragmentViewModel by viewModels()
        viewModel.getListOfComments(postId!!).observe(this, Observer<List<Comment>> {
            comments -> sendListToAdapter(comments)
        })

        sendButton.setOnClickListener {
            val commentBody = commentEditText.text
            viewModel.addNewComment(commentBody.toString(), postId)
            Toast.makeText(context, "You commented", Toast.LENGTH_SHORT).show()
            commentEditText.text.clear()
            commentEditText.clearFocus()
        }
    }

    private fun sendListToAdapter(comments: List<Comment>?) {
        binding?.commentsRecyclerView?.adapter = ListOfCommentsAdapter(comments!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}