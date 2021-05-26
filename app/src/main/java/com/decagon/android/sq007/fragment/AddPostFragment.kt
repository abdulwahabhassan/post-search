package com.decagon.android.sq007.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.FragmentAddPostBinding
import com.decagon.android.sq007.viewModel.AddPostFragmentViewModel
import com.decagon.android.sq007.viewModel.ListOfPostsFragmentViewModel
import com.google.android.material.textfield.TextInputEditText


class AddPostFragment : Fragment() {

    var binding: FragmentAddPostBinding? = null
    private lateinit var postButton: Button
    private lateinit var inputTextUserId: TextInputEditText
    private lateinit var inputTextBody: TextInputEditText
    private lateinit var inputTextTitle: TextInputEditText
    private lateinit var viewModel: AddPostFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddPostFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddPostBinding.inflate(inflater, container, false)

        postButton = binding?.postButton as Button
        inputTextBody = binding?.textInputBody as TextInputEditText
        inputTextTitle = binding?.textInputTitle as TextInputEditText
        inputTextUserId = binding?.textInputUserID as TextInputEditText

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postButton.setOnClickListener {

            val userId = inputTextUserId.text.toString()
            val title = inputTextTitle.text.toString()
            val body = inputTextBody.text.toString()

            viewModel.addNewPost(userId, title, body)
            Navigation.findNavController(requireView()).navigateUp()

        }
    }

}