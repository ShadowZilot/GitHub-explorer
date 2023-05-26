package com.example.githubexplorer.repository_info.ui

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubexplorer.databinding.FileItemBinding
import com.example.githubexplorer.repository_info.data.FileData

class FileViewHolder(
    private val mBinding: FileItemBinding
) : ViewHolder(mBinding.root), FileData.Mapper<Unit> {

    override fun map(name: String, path: String, url: String) {
        mBinding.fileName.text = name
    }
}