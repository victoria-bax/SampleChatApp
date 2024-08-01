package com.vicabax.samplechatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
        binding.userSelectionSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.switchUser()
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

    private fun setLoaded(state: ChatScreenState.Loaded) {
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
        }

    }

    private fun setLoading() {
        // todo
    }

    private fun setError(error: String) {
        // todo
    }
}
