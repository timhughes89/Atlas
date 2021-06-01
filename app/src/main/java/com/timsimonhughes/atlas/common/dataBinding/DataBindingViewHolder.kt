package com.timsimonhughes.atlas.common.dataBinding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.timsimonhughes.atlas.BR

class DataBindingViewHolder<T>(private var binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Any?) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}
