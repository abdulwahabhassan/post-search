package com.decagon.android.sq007.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.data.repository.MainRepository
import com.decagon.android.sq007.main.intent.MainIntent
import com.decagon.android.sq007.main.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchPost -> fetchPost()
                }
            }
        }
    }

    private fun fetchPost() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Posts(repository.getPosts())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }
}