package com.example.githubexplorer.repository_info.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.R
import com.example.githubexplorer.common.BaseFragment
import com.example.githubexplorer.common.LoadingErrorHandling
import com.example.githubexplorer.common.ResultLogic
import com.example.githubexplorer.databinding.RepositoryInfoFragmentBinding
import com.example.githubexplorer.repository_info.RepositoryInfoViewModel
import com.example.githubexplorer.search.data.DataListItem
import kotlinx.coroutines.launch

class RepositoryInfoFragment : BaseFragment<RepositoryInfoFragmentBinding>(
    R.layout.repository_info_fragment
), ResultLogic<List<DataListItem>> {
    override val mBinding: RepositoryInfoFragmentBinding by lazy {
        RepositoryInfoFragmentBinding.bind(view ?: throw Exception())
    }
    private val mViewModel by viewModels<RepositoryInfoViewModel> {
        RepositoryInfoViewModel.Factory()
    }
    private val mOwner by lazy {
        requireArguments().getString("owner")
    }
    private val mName by lazy {
        requireArguments().getString("name")
    }
    private val mPath by lazy {
        requireArguments().getString("path")
    }
    private val mLoadingError by lazy {
        LoadingErrorHandling.Base(mBinding.loadingErrorInclude)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.pathLabel.text = buildString {
            append(mName)
            append("/")
            append(mPath)
        }
        mLoadingError.setOnClickTryAgainListener {
            loadContent()
        }
        mBinding.contentList.adapter = RepositoryContentAdapter(
            mOwner ?: "",
            mName ?: ""
        )
        mBinding.contentList.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        mBinding.contentList.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        )
    }

    override fun onResume() {
        super.onResume()
        loadContent()
    }

    private fun loadContent() {
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.loadContent(
                mPath ?: "", mName ?: "",
                mOwner ?: ""
            ).collect {
                it.map(this@RepositoryInfoFragment)
            }
        }
    }

    override fun doIfLoading() {
        mLoadingError.isLoading(true)
        mBinding.contentList.visibility = View.GONE
    }

    override fun doIfSuccess(data: List<DataListItem>) {
        mLoadingError.isLoading(false)
        if (data.isNotEmpty()) {
            mBinding.contentList.visibility = View.VISIBLE
            (mBinding.contentList.adapter as RepositoryContentAdapter).setContents(data)
        } else {
            mLoadingError.showMessage(R.string.empty_repository)
        }
    }

    override fun doIfFailure(message: Int) {
        mLoadingError.isLoading(false)
        mLoadingError.showError(message)
        mBinding.contentList.visibility = View.GONE
    }
}