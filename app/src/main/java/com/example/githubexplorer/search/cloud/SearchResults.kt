package com.example.githubexplorer.search.cloud

import com.example.githubexplorer.common.auth_token
import com.example.githubexplorer.common.base_url
import com.example.githubexplorer.search.data.DataListItem
import com.example.githubexplorer.search.data.RepositoryData
import com.example.githubexplorer.search.data.UserData
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

interface SearchResults {

    suspend fun search(query: String) : List<DataListItem>

    class Users(
        private val mClient: OkHttpClient
    ) : SearchResults {

        override suspend fun search(query: String): List<DataListItem> {
            val response = mClient.newCall(Request.Builder()
                .addHeader("Authorization", "Bearer $auth_token")
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .addHeader("Accept", "application/vnd.github+json")
                .url("$base_url/search/users?q=$query&per_page=20&page=1")
                .build()).execute()
            return if (response.isSuccessful) {
                val responseBody = JSONObject(response.body!!.string())
                val dataArray = responseBody.getJSONArray("items")
                mutableListOf<DataListItem>().apply {
                    for (i in 0 until dataArray.length()) {
                        add(UserData(dataArray.getJSONObject(i)))
                    }
                }
            } else {
                throw Exception()
            }
        }
    }

    class Repositories(
        private val mClient: OkHttpClient
    ) : SearchResults {

        override suspend fun search(query: String): List<DataListItem> {
            val response = mClient.newCall(Request.Builder()
                .addHeader("Authorization", "Bearer $auth_token")
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .addHeader("Accept", "application/vnd.github+json")
                .url("$base_url/search/repositories?q=$query&per_page=20&page=1")
                .build()).execute()
            return if (response.isSuccessful) {
                val responseBody = JSONObject(response.body!!.string())
                val dataArray = responseBody.getJSONArray("items")
                mutableListOf<DataListItem>().apply {
                    for (i in 0 until dataArray.length()) {
                        add(RepositoryData(dataArray.getJSONObject(i)))
                    }
                }
            } else {
                throw Exception()
            }
        }
    }
}