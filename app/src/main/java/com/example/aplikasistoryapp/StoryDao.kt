package com.example.aplikasistoryapp

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<StoryResult>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, StoryResult>

    @Query("DELETE FROM story")
    fun deleteAll()
}