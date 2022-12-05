package com.example.aplikasistoryapp

data class StoryResponse (
    val error: Boolean? = null,
    val message: String = "",
    val listStory: List<StoryResult>
)