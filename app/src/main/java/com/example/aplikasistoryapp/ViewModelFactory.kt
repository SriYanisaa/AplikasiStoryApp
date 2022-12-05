package com.example.aplikasistoryapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val repository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelStory::class.java)){
            return ViewModelStory(repository) as T
        }
        if (modelClass.isAssignableFrom(ViewModelLogin::class.java)){
            return ViewModelLogin(repository) as T
        }
        if (modelClass.isAssignableFrom(ViewModelRegister::class.java)){
            return ViewModelRegister(repository) as T
        }
        if (modelClass.isAssignableFrom(ViewModelAddStory::class.java)){
            return ViewModelAddStory(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory{
            return instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}