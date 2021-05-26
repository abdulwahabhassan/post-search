package com.decagon.android.sq007

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.data.api.ApiHelperImpl
import com.decagon.android.sq007.data.api.RetrofitBuilder
import com.decagon.android.sq007.data.model.Post
import com.decagon.android.sq007.main.adapter.MainAdapter
import com.decagon.android.sq007.main.intent.MainIntent
import com.decagon.android.sq007.main.viewmodel.MainViewModel
import com.decagon.android.sq007.main.viewstate.MainState
import com.decagon.android.sq007.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())
    lateinit var recyclerView: RecyclerView
    lateinit var buttonFetchPost: Button
    lateinit var  progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        findViewById<Button>(R.id.buttonFetchPost).setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchPost)
            }
        }
    }


    private fun setupUI() {

         recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
         buttonFetchPost = findViewById<Button>(R.id.buttonFetchPost)
         progressBar = findViewById<ProgressBar>(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        ).get(MainViewModel::class.java)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        buttonFetchPost.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    is MainState.Posts -> {
                        progressBar.visibility = View.GONE
                        buttonFetchPost.visibility = View.GONE
                        renderList(it.post)
                    }
                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        buttonFetchPost.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(posts: List<Post>) {
        recyclerView.visibility = View.VISIBLE
        posts.let { listOfPosts -> listOfPosts.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }
}