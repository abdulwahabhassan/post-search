package com.decagon.android.sq007.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.adapter.ListOfPostsAdapter
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.FragmentListOfPostsBinding
import com.decagon.android.sq007.viewModel.ListOfPostsFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListOfPostsFragment : Fragment(), ListOfPostsAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    var binding: FragmentListOfPostsBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var addPostButton: FloatingActionButton
    private lateinit var viewModel: ListOfPostsFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListOfPostsFragmentViewModel::class.java)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListOfPostsBinding.inflate(inflater, container, false)

        recyclerView = binding?.postRecyclerView as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchView = binding?.searchView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)

        addPostButton = binding?.addPostButton as FloatingActionButton

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListOfPosts().observe(viewLifecycleOwner, Observer<List<Post>> {
                posts -> sendListToAdapter(posts)
        })

        addPostButton.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_listOfPostsFragment_to_addPostFragment
            )
        }

    }


    override fun onItemClick(post: Post, itemView: View) {
        val postBundle = Bundle().apply {
            putString(getString(R.string.post_body), post.body)
            putString(getString(R.string.post_title), post.title)
            putInt(getString(R.string.post_id), post.id!!)
        }
        view?.findNavController()
            ?.navigate(R.id.action_listOfPostsFragment_to_postPageFragment, postBundle)

    }

    private fun sendListToAdapter(posts: List<Post>) {
        binding?.postRecyclerView?.adapter = ListOfPostsAdapter(posts, this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.searchPosts(query)
        return true
    }

    override fun onClose(): Boolean {
        viewModel.getListOfPosts()
        searchView.onActionViewCollapsed()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
       viewModel.searchPosts(newText)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}