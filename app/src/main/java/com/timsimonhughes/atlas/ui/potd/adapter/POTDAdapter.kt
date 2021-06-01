package com.timsimonhughes.atlas.ui.potd.adapter

import androidx.recyclerview.widget.DiffUtil
import com.timsimonhughes.atlas.R
import com.timsimonhughes.atlas.common.dataBinding.DataBindingAdapter
import com.timsimonhughes.atlas.model.potd.POTD

class POTDAdapter : DataBindingAdapter<POTD>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<POTD>() {
        override fun areContentsTheSame(oldItem: POTD, newItem: POTD) = oldItem.url == newItem.url
        override fun areItemsTheSame(oldItem: POTD, newItem: POTD) = oldItem.title == newItem.title
    }

    override fun getItemViewType(position: Int) = R.layout.list_item_potd
}