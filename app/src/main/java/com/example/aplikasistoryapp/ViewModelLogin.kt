package com.example.aplikasistoryapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModelLogin(private val storyRepository: StoryRepository): ViewModel() {
    fun login(email: String, password: String) = storyRepository.login(email, password)

    fun saveUser(user: UserModel){
        viewModelScope.launch {
            storyRepository.saveUser(user)
        }
    }

    fun login(){
        viewModelScope.launch {
            storyRepository.login()
        }
    }

    fun logout(){
        viewModelScope.launch {
            storyRepository.logout()
        }
    }
}