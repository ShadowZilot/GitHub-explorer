package com.example.githubexplorer.file_info

import android.os.Bundle
import android.view.View
import com.example.githubexplorer.R
import com.example.githubexplorer.common.BaseFragment
import com.example.githubexplorer.databinding.FileInfoFragmentBinding

class FileInfoFragment : BaseFragment<FileInfoFragmentBinding>(R.layout.file_info_fragment) {
    override val mBinding by lazy {
        FileInfoFragmentBinding.bind(view ?: throw Exception())
    }
    private val mName by lazy {
        requireArguments().getString("name")
    }
    private val mPath by lazy {
        requireArguments().getString("path")
    }
    private val mFileUrl by lazy {
        requireArguments().getString("file_url")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.pathLabel.text = buildString {
            append(mName)
            append("/")
            append(mPath)
        }
        mBinding.fileView.settings.javaScriptEnabled = true
        mBinding.fileView.loadUrl(mFileUrl ?: "")
    }
}