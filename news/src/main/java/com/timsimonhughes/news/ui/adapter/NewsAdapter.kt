package com.timsimonhughes.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timsimonhughes.news.databinding.ListItemNewsBinding
import com.timsimonhughes.news.model.News

class NewsAdapter(private val clickListener: NewsItemClickListener) :
    ListAdapter<News, NewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ListItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: News, newItem: News) =
                oldItem.title == newItem.title
        }
    }

    interface NewsItemClickListener {
        fun onItemClick(sharedView: View, newsItem: News)
    }
}

class NewsViewHolder(
    private val binding: ListItemNewsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(newsItem: News, onClick: NewsAdapter.NewsItemClickListener) {
        binding.run {
            this.item = newsItem
            clickListener = onClick
            executePendingBindings()
        }
    }
}