package com.vicabax.samplechatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vicabax.samplechatapp.R
import com.vicabax.samplechatapp.databinding.ActivityMainBinding
import com.vicabax.samplechatapp.ui.MessagesListViewModel.ChatScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {

    private val viewModel: MessagesListViewModel by viewModels()

    private val adapter: MessagesAdapter = MessagesAdapter()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        ActivityMainBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.messagesList.adapter = adapter
        setScrollOnInsertedMessages()
        binding.userSelectionSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.switchUser()
        }
        binding.messageInput.addTextChangedListener { text ->
            binding.buttonSend.isEnabled = !text.isNullOrEmpty()
        }
        binding.buttonSend.setOnClickListener {
            viewModel.sendMessage(binding.messageInput.text.toString())
            binding.messageInput.text.clear()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {

                launch {
                    viewModel.state.collect {
                        when (it) {
                            is ChatScreenState.Loaded -> setLoaded(it)
                            ChatScreenState.Loading -> setLoading()
                            is ChatScreenState.Error -> setError(it.message)
                        }
                    }
                }
            }
        }
    }

    private fun setScrollOnInsertedMessages() {
        adapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.messagesList.scrollToPosition(positionStart + itemCount - 1)
            }
        })
    }

    private fun setLoaded(state: ChatScreenState.Loaded) {
        binding.progress.isVisible = false
        binding.userSelectionSwitch.isVisible = true
        state.friend.let { user ->
            binding.userName.text = user.name
            binding.userIcon.visibility = if (user.image == null) {
                View.GONE
            } else {
                View.VISIBLE
            }
            user.image?.let { res ->
                Glide.with(this@MainActivity)
                    .load(res)
                    .circleCrop()
                    .into(binding.userIcon)
            }
        }
        state.loggedUser.let { user ->
            binding.userSelectionSwitch.text = getString(R.string.active_user_switch, user.name)
        }
        state.messages.let { messages ->
            adapter.submitList(messages)
//            binding.messagesList.scrollToPosition(messages.size - 1);
        }

    }

    private fun setLoading() {
        binding.progress.isVisible = true
        binding.userSelectionSwitch.isVisible = false
    }

    private fun setError(error: String) {
        binding.progress.isVisible = false
        binding.userSelectionSwitch.isVisible = false

        // todo
    }
}
