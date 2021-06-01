package com.timsimonhughes.spacex.ui.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timsimonhughes.spacex.databinding.ListItemLaunchesBinding
import com.timsimonhughes.spacex.model.Capsule

class LaunchesAdapter : ListAdapter<Capsule, LaunchesViewHolder>(
    DIFF_CALLBACK
) {

    private val scrollStates = hashMapOf<String, Parcelable>()
    var capsuleList: ArrayList<Capsule>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder {
        return LaunchesViewHolder(
            ListItemLaunchesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onViewRecycled(holder: LaunchesViewHolder) {
        super.onViewRecycled(holder)
    }

    fun setItems(itemList: ArrayList<Capsule>) {
        this.capsuleList = itemList
    }

    fun addItem(item: Capsule) {
        this.capsuleList?.add(item)
    }

    fun removeItem(item: Capsule) {
        this.removeItem(item)
    }

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Capsule>() {
            override fun areItemsTheSame(oldItem: Capsule, newItem: Capsule) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Capsule, newItem: Capsule) =
                oldItem._id == newItem._id
        }
    }
}

class LaunchesViewHolder(
    private val binding: ListItemLaunchesBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Capsule) {
        binding.run {
            this.item = item
        }
    }
}