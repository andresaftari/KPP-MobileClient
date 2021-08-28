package com.kppmining.client.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kppmining.client.databinding.ItemHistoryBinding
import com.kppmining.core.domain.model.DummyHistory

class HistoryAdapter(private val items: ArrayList<DummyHistory>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        with(holder) { bind(items[position]) }

    override fun getItemCount() = items.size

    class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DummyHistory) = with(binding) {
            tvActionTitle.text = item.actionName
            tvActionDate.text = item.actionDate
            tvActionTime.text = item.actionTime
        }
    }
}