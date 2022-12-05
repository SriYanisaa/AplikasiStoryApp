package com.example.aplikasistoryapp

import android.media.session.MediaSession.Token
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(
    private val preference: UserPreference,
    private val apiService: ApiService,
    private val database: StoryDatabase) {

    @OptIn(ExperimentalPagingApi::class)
    fun getStory(): LiveData<PagingData<StoryResult>>{
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = StoryRemoteMediator(database, apiService, preference),
            pagingSourceFactory = {database.storyDao().getAllStory()}
        ).liveData
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.daftar(RegisterRequest(name, email, password))
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.d("Register", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody, lat: Double?, lon: Double?):
            LiveData<Result<AddStoryResponse>> = liveData {

        emit(Result.Loading)
        try {
            val response = apiService.postStory(token, file, description, lat, lon)
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.d("Register", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getLocation(token: String): LiveData<Result<StoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getLocation(token, 1)
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.d("Register", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUser(): LiveData<UserModel>{
        return preference.getUser().asLiveData()
    }

    suspend fun saveUser(user: UserModel){
        preference.saveUser(user)
    }

    suspend fun login(){
        preference.login()
    }

    suspend fun logout(){
        preference.logout()
    }

    companion object{
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(preference: UserPreference, apiService: ApiService, database: StoryDatabase): StoryRepository =
            instance ?: synchronized(this){
                instance ?: StoryRepository(preference, apiService, database)
            }.also { instance = it }
    }
}