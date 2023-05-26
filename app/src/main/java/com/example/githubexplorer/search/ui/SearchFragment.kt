package com.example.githubexplorer.search.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.R
import com.example.githubexplorer.common.BaseFragment
import com.example.githubexplorer.common.LoadingErrorHandling
import com.example.githubexplorer.common.ResultLogic
import com.example.githubexplorer.databinding.SearchFragmentBinding
import com.example.githubexplorer.search.SearchViewModel
import com.example.githubexplorer.search.data.DataListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<SearchFragmentBinding>(R.layout.search_fragment),
    ResultLogic<List<DataListItem>> {
    override val mBinding: SearchFragmentBinding by lazy {
        SearchFragmentBinding.bind(view ?: throw Exception())
    }
    private val mLoadingError by lazy {
        LoadingErrorHandling.Base(mBinding.loadingErrorInclude)
    }
    private lateinit var mTimer: Job
    private val mViewModel by viewModels<SearchViewModel> {
        SearchViewModel.Factory()
    }
    private val mAdapter = SearchResultAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mLoadingError.isLoading(false)
        mLoadingError.setOnClickTryAgainListener {
            implementSearch()
        }
        mBinding.submitSearchButton.setOnClickListener {
            implementSearch()
        }
        mBinding.searchList.adapter = mAdapter
        mBinding.searchList.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        mBinding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (::mTimer.isInitialized)
                    mTimer.cancel()
                mTimer = lifecycleScope.launch {
                    delay(1000)
                    implementSearch()
                }
            }
        })
        implementSearch()
    }

    private fun implementSearch() {
        if (mBinding.searchField.text.toString().length >= 3) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mViewModel.searchByQuery(mBinding.searchField.text.toString())
                        .collect {
                            it.map(this@SearchFragment)
                        }
                }
            }
        } else {
            mBinding.searchList.visibility = View.GONE
            mLoadingError.showMessage(R.string.empty_search)
        }

    }

    override fun doIfLoading() {
        mBinding.searchField.isEnabled = false
        mBinding.submitSearchButton.isEnabled = false
        mLoadingError.isLoading(true)
        mBinding.searchList.visibility = View.GONE
    }

    override fun doIfSuccess(data: List<DataListItem>) {
        mBinding.searchField.isEnabled = true
        mBinding.submitSearchButton.isEnabled = true
        mLoadingError.isLoading(false)
        if (data.isEmpty() && mBinding.searchField.text.toString().isEmpty()) {
            mLoadingError.showMessage(R.string.empty_search)
        } else if (data.isEmpty() && mBinding.searchField.text.toString().isNotEmpty()) {
            mLoadingError.showMessage(R.string.not_found)
        } else {
            mBinding.searchList.visibility = View.VISIBLE
            mAdapter.setSearchResult(data)
        }
        mBinding.searchField.requestFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mBinding.searchField, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun doIfFailure(message: Int) {
        mBinding.searchField.isEnabled = true
        mBinding.submitSearchButton.isEnabled = true
        mLoadingError.isLoading(false)
        mLoadingError.showError(R.string.something_went_wrong)
    }
}