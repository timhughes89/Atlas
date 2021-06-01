package com.timsimonhughes.spacex.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timsimonhughes.spacex.databinding.ListItemLaunchesBinding
import com.timsimonhughes.spacex.model.Capsule

class CapsulesAdapter(private val clickListener: CapsuleClickListener) : RecyclerView.Adapter<CapsulesViewHolder>() {

    private var capsuleList: ArrayList<Capsule>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapsulesViewHolder {
        return CapsulesViewHolder(
            ListItemLaunchesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() : Int = capsuleList?.size!!

    override fun onBindViewHolder(holder: CapsulesViewHolder, position: Int) {
        capsuleList?.get(position)?.let { holder.bind(it, clickListener) }
    }

    fun submitList(items: ArrayList<Capsule>) {
        this.capsuleList = items
    }

    fun addItem(item: Capsule) {
        capsuleList?.add(item)
    }

    fun removeItem(capsule: Capsule?) {
        capsuleList?.remove(capsule)
    }
}

interface CapsuleClickListener {
    fun onCapsuleClicked(sharedView: View, capsule: Capsule)
}

class CapsulesViewHolder(private val binding: ListItemLaunchesBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Capsule,
        clickListener: CapsuleClickListener
    ) {
        binding.run {
            this.item = item
            this.listener = clickListener
            executePendingBindings()
        }
    }
}