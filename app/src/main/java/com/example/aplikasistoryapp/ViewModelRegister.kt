package com.example.aplikasistoryapp

import androidx.lifecycle.ViewModel

class ViewModelRegister(private val storyRepository: StoryRepository): ViewModel() {
    fun userRegister(name: String, email: String, password: String ) =
        storyRepository.register(name, email, password)
}