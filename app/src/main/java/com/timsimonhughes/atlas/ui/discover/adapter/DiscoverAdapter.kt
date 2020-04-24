package com.timsimonhughes.atlas.ui.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timsimonhughes.atlas.R
import com.timsimonhughes.atlas.model.Planet
import kotlinx.android.extensions.LayoutContainer
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.list_item_planets.view.*


class DiscoverAdapter : ListAdapter<Planet, DiscoverAdapter.DiscoverViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        return DiscoverViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item_planets,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DiscoverViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(planet: Planet) {
            itemView.apply {

                Glide.with(containerView.context)
                    .load(planet.imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(image_view_discover_image)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Planet>() {
            override fun areItemsTheSame(oldItem: Planet, newItem: Planet) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Planet, newItem: Planet) =
                oldItem.title == newItem.title
        }
    }

    interface DisoverItemClickListener {
        fun onItemClick(position: Int, sharedView: View?, planet: Planet?)
    }
}