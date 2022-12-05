package com.example.aplikasistoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.aplikasistoryapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Story"

        val data = intent.getParcelableExtra<StoryResult>(DETAIL_STORY)

        with(binding){
            txtUser.text = data?.name
            txtDescription.text = data?.description
            Glide.with(this@DetailStoryActivity)
                .load(data?.photoUrl)
                .into(imgReceive)
        }
    }

    companion object{
        const val DETAIL_STORY = "detail_story"
    }
}