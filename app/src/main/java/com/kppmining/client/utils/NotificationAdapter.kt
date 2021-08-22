package com.kppmining.client.utils

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kppmining.client.databinding.ItemNotificationBinding
import com.kppmining.core.domain.model.DummyNotification

class NotificationAdapter(private val items: ArrayList<DummyNotification>) :
    RecyclerView.Adapter<NotificationAdapter.NotifViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotifViewHolder(
        ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) =
        with(holder) { bind(items[position]) }

    override fun getItemCount() = items.size

    class NotifViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DummyNotification) = with(binding) {
            if (item.notifStatus != "Peringatan")
                itemView.setBackgroundColor(Color.parseColor("#EBFAF7"))

            Glide.with(root)
                .load(item.notifIcon)
                .into(iconStatus)

            tvStatus.text = item.notifStatus
            tvDate.text = item.notifDate
            tvTime.text = item.notifTime
            tvTitle.text = item.notifTitle
            tvText.text = item.notifText
        }
    }
}