package com.example.aplikasistoryapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "story")
data class StoryResult (
        @PrimaryKey val id: String,
        val name: String? = null,
        val description: String? = null,
        val photoUrl: String? = null,
        val createdAt: String? = null,
        val lat: Double? = 0.0,
        val lon: Double? = 0.0
): Parcelable