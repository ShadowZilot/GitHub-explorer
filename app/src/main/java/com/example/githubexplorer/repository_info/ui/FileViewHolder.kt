package com.example.githubexplorer.repository_info.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.FileItemBinding
import com.example.githubexplorer.repository_info.data.FileData

class FileViewHolder(
    private val mBinding: FileItemBinding
) : ViewHolder(mBinding.root), FileData.Mapper<Unit> {

    override fun map(name: String, path: String, url: String) {
        mBinding.fileName.text = name
        mBinding.root.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_repositoryInfoFragment_to_fileInfoFragment,
                Bundle().apply {
                    putString("path", path)
                    putString("name", name)
                    putString("file_url", url)
                }
            )
        }
    }
}