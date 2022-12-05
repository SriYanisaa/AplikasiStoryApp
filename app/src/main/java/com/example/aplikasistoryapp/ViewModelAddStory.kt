package com.example.aplikasistoryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ViewModelAddStory(private val storyRepository: StoryRepository) : ViewModel() {
    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double?,
        lon: Double?) = storyRepository.addStory(token, file, description, lat, lon)

    fun getUser(): LiveData<UserModel>{
        return storyRepository.getUser()
    }
}