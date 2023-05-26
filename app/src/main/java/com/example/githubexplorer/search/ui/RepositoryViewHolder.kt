package com.example.githubexplorer.search.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.RepositoryItemBinding
import com.example.githubexplorer.search.data.RepositoryData

class RepositoryViewHolder(
    private val mBinding: RepositoryItemBinding
) : ViewHolder(mBinding.root), RepositoryData.Mapper<Unit> {

    override fun map(
        id: Long,
        name: String,
        owner: String,
        link: String,
        forksCount: Int,
        description: String
    ) {
        mBinding.repositoryName.text = name
        mBinding.repositoryDescription.text = description
        mBinding.forksCount.text = buildString {
            append(forksCount)
            append("\nforks")
        }
        mBinding.root.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_searchFragment_to_repositoryInfoFragment,
                Bundle().apply {
                    putString("name", name)
                    putString("owner", owner)
                    putString("path", "")
                }
            )
        }
    }
}