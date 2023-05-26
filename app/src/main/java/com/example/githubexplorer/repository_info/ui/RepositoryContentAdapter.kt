package com.example.githubexplorer.repository_info.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubexplorer.databinding.DirectoryItemBinding
import com.example.githubexplorer.databinding.FileItemBinding
import com.example.githubexplorer.search.data.DataListItem

class RepositoryContentAdapter(
    private val mOwner: String,
    private val mName: String
) : Adapter<ViewHolder>() {
    private val mContent = mutableListOf<DataListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            DirViewHolder(
                DirectoryItemBinding.inflate(inflater, parent, false),
                mOwner,
                mName
            )
        } else {
            FileViewHolder(
                FileItemBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun getItemCount() = mContent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mContent[position].bindViewHolder(holder)
    }

    override fun getItemViewType(position: Int) = mContent[position].viewType()

    fun setContents(content: List<DataListItem>) {
        mContent.clear()
        mContent.addAll(content)
        notifyDataSetChanged()
    }
}