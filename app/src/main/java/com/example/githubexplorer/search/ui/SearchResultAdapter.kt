package com.example.githubexplorer.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubexplorer.databinding.RepositoryItemBinding
import com.example.githubexplorer.databinding.UserItemBinding
import com.example.githubexplorer.search.data.DataListItem

class SearchResultAdapter : Adapter<ViewHolder>() {
    private val mSearchList = mutableListOf<DataListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 2) {
            RepositoryViewHolder(
                RepositoryItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        } else {
            UserViewHolder(
                UserItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = mSearchList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mSearchList[position].bindViewHolder(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return mSearchList[position].viewType()
    }

    fun setSearchResult(result: List<DataListItem>) {
        mSearchList.clear()
        mSearchList.addAll(result)
        notifyDataSetChanged()
    }
}