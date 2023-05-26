package com.example.githubexplorer.repository_info.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.DirectoryItemBinding
import com.example.githubexplorer.repository_info.data.DirData

class DirViewHolder(
    private val mBinding: DirectoryItemBinding,
    private val mOwner: String,
    private val mRepositoryName: String
) : ViewHolder(mBinding.root), DirData.Mapper<Unit> {

    override fun map(name: String, path: String) {
        mBinding.dirName.text = name
        mBinding.root.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_repositoryInfoFragment_self,
                Bundle().apply {
                    putString("name", mRepositoryName)
                    putString("owner", mOwner)
                    putString("path", path)
                }
            )
        }
    }
}