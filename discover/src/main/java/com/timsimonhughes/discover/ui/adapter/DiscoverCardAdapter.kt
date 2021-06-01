package com.timsimonhughes.discover.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timsimonhughes.discover.databinding.ListItemDiscoverCardBinding
import com.timsimonhughes.discover.model.Planet

class DiscoverCardAdapter(private val discoverClickListener: DiscoverClickListener) : RecyclerView.Adapter<DiscoverCardViewHolder>() {

    private var items: ArrayList<Planet>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverCardViewHolder {
        return DiscoverCardViewHolder(
            ListItemDiscoverCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() : Int = items?.size!!

    override fun onBindViewHolder(holder: DiscoverCardViewHolder, position: Int) {
        items?.get(position)?.let { holder.bind(it, discoverClickListener) }
    }

    fun submitList(items: ArrayList<Planet>) {
        this.items = items
    }

    fun addItem(item: Planet) {
        items?.add(item)
    }

    fun removeItem(capsule: Planet?) {
        items?.remove(capsule)
    }
}

interface DiscoverClickListener {
    fun onItemClick(sharedView: View, planetItem: Planet)
}

class DiscoverCardViewHolder(private val binding: ListItemDiscoverCardBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Planet,
        clickListener: DiscoverClickListener) {
        binding.run {
            this.item = item
            this.clickHandler = clickListener
            executePendingBindings()
        }
    }
}