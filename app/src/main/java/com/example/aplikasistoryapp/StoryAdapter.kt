package com.example.aplikasistoryapp

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasistoryapp.databinding.ItemRowStoryBinding

class StoryAdapter: PagingDataAdapter<StoryResult, StoryAdapter.ListViewHolder>(DIFF_CALLBACK){
    inner class ListViewHolder(private val binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        internal fun bind(item: StoryResult){
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java).apply {
                    putExtra(DetailStoryActivity.DETAIL_STORY, item)
                }
                val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.imageStory, "photo"),
                    Pair(binding.txtUsername, "name")
                )
                it.context.startActivity(intent, optionsCompat.toBundle())
            }
            binding.txtUsername.text = item.name
            Glide.with(itemView.context)
                .load(item.photoUrl)
                .apply(RequestOptions().override(200,200))
                .into(binding.imageStory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResult>(){
            override fun areItemsTheSame(oldItem: StoryResult, newItem: StoryResult): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryResult, newItem: StoryResult): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}