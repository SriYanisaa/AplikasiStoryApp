package com.example.aplikasistoryapp

import android.media.session.MediaSession.Token

data class UserModel(
    val name: String,
    val token: String,
    val isLogin: Boolean
)
