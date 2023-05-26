package com.example.githubexplorer.repository_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubexplorer.GLOBAL_CLIENT
import com.example.githubexplorer.R
import com.example.githubexplorer.common.ResultData
import com.example.githubexplorer.repository_info.cloud.RepositoryInfoCloud
import com.example.githubexplorer.search.data.DataListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryInfoViewModel(
    private val mRepositoryContent: RepositoryInfoCloud
) : ViewModel() {

    suspend fun loadContent(
        path: String,
        name: String, owner: String
    ): Flow<ResultData<List<DataListItem>>> {
        return flow {
            emit(object : ResultData<List<DataListItem>>(mIsLoading = true) {})
            try {
                val content = mRepositoryContent.content(path, name, owner)
                emit(object : ResultData<List<DataListItem>>(mData = content) {})
            } catch (e: Exception) {
                emit(object : ResultData<List<DataListItem>>(
                    mMessage = R.string.something_went_wrong
                ) {})
            }
        }.flowOn(Dispatchers.IO)
    }

    class Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RepositoryInfoViewModel(
                RepositoryInfoCloud.Base(
                    GLOBAL_CLIENT
                )
            ) as T
        }
    }
}