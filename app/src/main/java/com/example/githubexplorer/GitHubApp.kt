package com.example.githubexplorer

import android.app.Application
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import okhttp3.OkHttpClient

val GLOBAL_CLIENT = OkHttpClient()

class GitHubApp : Application() {
}