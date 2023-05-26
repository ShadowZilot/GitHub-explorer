package com.example.githubexplorer.repository_info.cloud

import com.example.githubexplorer.common.auth_token
import com.example.githubexplorer.common.base_url
import com.example.githubexplorer.repository_info.data.DirData
import com.example.githubexplorer.repository_info.data.FileData
import com.example.githubexplorer.search.data.DataListItem
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

interface RepositoryInfoCloud {

    suspend fun content(path: String, name: String, owner: String): List<DataListItem>

    class Base(
        private val mClient: OkHttpClient
    ) : RepositoryInfoCloud {
        override suspend fun content(
            path: String, name: String,
            owner: String
        ): List<DataListItem> {
            val response = mClient.newCall(
                Request.Builder()
                    .addHeader("Authorization", "Bearer $auth_token")
                    .addHeader("X-GitHub-Api-Version", "2022-11-28")
                    .addHeader("Accept", "application/vnd.github+json")
                    .url("$base_url/repos/$owner/$name/contents/$path")
                    .build()
            ).execute()
            if (response.isSuccessful) {
                val responseBody = JSONArray(response.body!!.string())
                return mutableListOf<DataListItem>().apply {
                    for (i in 0 until responseBody.length()) {
                        add(
                            if (responseBody.getJSONObject(i).getString("type") == "file") {
                                FileData(
                                    responseBody.getJSONObject(i)
                                )
                            } else {
                                DirData(
                                    responseBody.getJSONObject(i)
                                )
                            }
                        )
                    }
                }
            } else {
                throw Exception()
            }
        }
    }
}