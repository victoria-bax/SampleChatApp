package com.vicabax.samplechatapp.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vicabax.samplechatapp.R
import com.vicabax.samplechatapp.databinding.MessageItemIncomingBinding
import com.vicabax.samplechatapp.databinding.MessageItemOutgoingBinding
import com.vicabax.samplechatapp.ui.model.MessageUiModel
import com.vicabax.samplechatapp.ui.model.MessageUiModel.IncomingMessageUiModel
import com.vicabax.samplechatapp.ui.model.MessageUiModel.OutgoingMessageUiModel
import com.vicabax.samplechatapp.ui.model.OutgoingMessageStatus

class MessagesAdapter : ListAdapter<MessageUiModel, MessageViewHolder<MessageUiModel>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<MessageUiModel> =
        when (viewType) {
            MessageUiModel.INCOMING_TYPE -> IncomingViewHolder(
                MessageItemIncomingBinding.inflate(parent.inflater(), parent, false)
            )

            MessageUiModel.OUTGOING_TYPE -> OutgoingViewHolder(
                MessageItemOutgoingBinding.inflate(parent.inflater(), parent, false)
            )
            //todo: timestamp type
            else -> error("Unknown viewType: $viewType")
        } as MessageViewHolder<MessageUiModel>

    override fun onBindViewHolder(holder: MessageViewHolder<MessageUiModel>, position: Int) =
        holder.bind(getItem(position))

    override fun getItemViewType(position: Int): Int =
        getItem(position).type


    companion object {


        private val diffCallback = object : DiffUtil.ItemCallback<MessageUiModel>() {
            override fun areItemsTheSame(
                oldItem: MessageUiModel,
                newItem: MessageUiModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MessageUiModel,
                newItem: MessageUiModel
            ): Boolean = oldItem == newItem
        }
    }
}

abstract class MessageViewHolder<T : MessageUiModel>(itemView: View) :
    ViewHolder(itemView) {
    abstract fun bind(message: T)
}

private class IncomingViewHolder(private val binding: MessageItemIncomingBinding) :
    MessageViewHolder<IncomingMessageUiModel>(binding.root) {
    override fun bind(message: IncomingMessageUiModel) {
        binding.message.text = message.text
    }
}

private class OutgoingViewHolder(private val binding: MessageItemOutgoingBinding) :
    MessageViewHolder<OutgoingMessageUiModel>(binding.root) {
    override fun bind(message: OutgoingMessageUiModel) {
        binding.message.text = message.text
        binding.messageSendStatus.apply {
            visibility = when (message.status) {
                OutgoingMessageStatus.SENDING -> GONE
                OutgoingMessageStatus.SENT -> {
                    setImageResource(R.drawable.message_sent_icon)
                    VISIBLE
                }

                OutgoingMessageStatus.DELIVERED -> {
                    setImageResource(R.drawable.message_delivered_icon)
                    VISIBLE
                }
            }
        }

    }
}
