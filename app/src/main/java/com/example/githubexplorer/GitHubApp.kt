package com.example.githubexplorer

import android.app.Application
import okhttp3.OkHttpClient

val GLOBAL_CLIENT = OkHttpClient()

class GitHubApp : Application()