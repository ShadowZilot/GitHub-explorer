package com.example.githubexplorer.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubexplorer.GLOBAL_CLIENT
import com.example.githubexplorer.R
import com.example.githubexplorer.common.ResultData
import com.example.githubexplorer.search.cloud.SearchResults
import com.example.githubexplorer.search.data.DataListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchViewModel(
    private val mRepositories: SearchResults.Repositories,
    private val mUsers: SearchResults.Users
) : ViewModel() {

    suspend fun searchByQuery(query: String): Flow<ResultData<List<DataListItem>>> {
        return flow {
            emit(object : ResultData<List<DataListItem>>(mIsLoading = true) {})
            emit(
                if (query.isEmpty()) {
                    object : ResultData<List<DataListItem>>(mData = emptyList()) {}
                } else {
                    try {
                        coroutineScope {
                            val users = async { mUsers.search(query) }
                            val repositories = async { mRepositories.search(query) }
                            val summaryList = users.await() + repositories.await()
                            object : ResultData<List<DataListItem>>(mData = summaryList.sortedBy {
                                it.sortProperty()
                            }) {}
                        }
                    } catch (e: Exception) {
                        object :
                            ResultData<List<DataListItem>>(mMessage = R.string.something_went_wrong) {}
                    }
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    class Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(
                SearchResults.Repositories(GLOBAL_CLIENT),
                SearchResults.Users(
                    GLOBAL_CLIENT
                )
            ) as T
        }
    }
}
