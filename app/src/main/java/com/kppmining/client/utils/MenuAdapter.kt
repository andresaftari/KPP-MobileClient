package com.kppmining.client.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kppmining.client.databinding.ItemMenuBinding
import com.kppmining.core.domain.model.Menus

class MenuAdapter(val items: ArrayList<Menus>, var handler: (Int, Menus) -> Unit) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MenuViewHolder(
            ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            bind(item)
            binding.root.setOnClickListener { handler(position, item) }
        }
    }

    override fun getItemCount() = items.size

    class MenuViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menus: Menus) = with(binding) {
            Glide.with(this.root)
                .load(menus.thumbnail)
                .into(ivActionmenu)

            tvActionmenu.text = menus.name
        }
    }
}