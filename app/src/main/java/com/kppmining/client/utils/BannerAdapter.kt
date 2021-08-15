package com.kppmining.client.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kppmining.client.databinding.ItemBannerBinding
import com.kppmining.core.domain.model.DummyBanner

class BannerAdapter(private val items: ArrayList<DummyBanner>) :
    RecyclerView.Adapter<BannerAdapter.SliderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SliderViewHolder(
        ItemBannerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        with(holder) { bind(items[position]) }
    }

    override fun getItemCount() = items.size

    class SliderViewHolder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DummyBanner) = with(binding) {
            Glide.with(this.root)
                .load(item.bannerThumb)
                .into(ivBanner)

            tvBannerTitle.text = item.bannerTitle
            tvBannerSubtitle.text = item.bannerSubtitle
        }
    }
}
