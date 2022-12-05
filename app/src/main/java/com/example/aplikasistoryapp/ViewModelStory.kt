package com.example.aplikasistoryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

class ViewModelStory (private val repository: StoryRepository) : ViewModel() {
    fun getStory() : LiveData<PagingData<StoryResult>>{
        return repository.getStory().cachedIn(viewModelScope)
    }

    fun getUser() : LiveData<UserModel>{
        return repository.getUser()
    }
}