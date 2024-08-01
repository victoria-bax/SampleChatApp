package com.vicabax.samplechatapp.ui

import android.content.Context
import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vicabax.samplechatapp.R
import com.vicabax.samplechatapp.databinding.MessageItemIncomingBinding
import com.vicabax.samplechatapp.databinding.MessageItemOutgoingBinding
import com.vicabax.samplechatapp.databinding.MessageItemTimeSeparatorBinding
import com.vicabax.samplechatapp.ui.model.MessageUiModel
import com.vicabax.samplechatapp.ui.model.MessageUiModel.DateTimeUiModel
import com.vicabax.samplechatapp.ui.model.MessageUiModel.IncomingMessageUiModel
import com.vicabax.samplechatapp.ui.model.MessageUiModel.OutgoingMessageUiModel
import com.vicabax.samplechatapp.ui.model.OutgoingMessageStatus
import com.vicabax.samplechatapp.ui.model.SeparatorType

class MessagesAdapter :
    ListAdapter<MessageUiModel, MessageViewHolder<MessageUiModel>>(diffCallback) {
    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder<MessageUiModel> =
        when (viewType) {
            MessageUiModel.INCOMING_TYPE -> IncomingViewHolder(
                MessageItemIncomingBinding.inflate(parent.inflater(), parent, false)
            )

            MessageUiModel.OUTGOING_TYPE -> OutgoingViewHolder(
                MessageItemOutgoingBinding.inflate(parent.inflater(), parent, false)
            )

            MessageUiModel.TIMESTAMP_TYPE -> TimestampViewHolder(
                MessageItemTimeSeparatorBinding.inflate(parent.inflater(), parent, false)
            )

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

    protected fun getContext(): Context = itemView.context

    protected fun View.setSeparator(separatorType: SeparatorType) {
        val paddingTop = context.resources.getDimensionPixelSize(
            when (separatorType) {
                SeparatorType.SMALL -> R.dimen.padding_small
                SeparatorType.BIG -> R.dimen.padding_medium
            }
        )
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }
}

private class IncomingViewHolder(private val binding: MessageItemIncomingBinding) :
    MessageViewHolder<IncomingMessageUiModel>(binding.root) {
    override fun bind(message: IncomingMessageUiModel) {
        binding.message.text = message.text
        binding.root.setSeparator(message.separator)
    }
}

private class OutgoingViewHolder(private val binding: MessageItemOutgoingBinding) :
    MessageViewHolder<OutgoingMessageUiModel>(binding.root) {
    override fun bind(message: OutgoingMessageUiModel) {
        binding.message.text = message.text
        binding.root.setSeparator(message.separator)
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

private class TimestampViewHolder(private val binding: MessageItemTimeSeparatorBinding) :
    MessageViewHolder<DateTimeUiModel>(binding.root) {
    override fun bind(message: DateTimeUiModel) {
        binding.dayText.text =
            Html.fromHtml(
                getContext().getString(R.string.day_time_format, message.day, message.time),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
    }
}
