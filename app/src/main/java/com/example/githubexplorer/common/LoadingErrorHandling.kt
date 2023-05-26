package com.example.githubexplorer.common

import android.support.annotation.StringRes
import android.view.View
import android.view.View.OnClickListener
import com.example.githubexplorer.databinding.LoadingErrorViewBinding

interface LoadingErrorHandling {

    fun isLoading(state: Boolean)

    fun showError(@StringRes messageId: Int)

    fun showMessage(@StringRes messageId: Int)

    fun setOnClickTryAgainListener(listener: OnClickListener)

    class Base(
        private val mErrorLoadingView: LoadingErrorViewBinding

    ) : LoadingErrorHandling {

        override fun isLoading(state: Boolean) {
            if (state) {
                mErrorLoadingView.loadingBar.visibility = View.VISIBLE
                mErrorLoadingView.errorLabel.visibility = View.GONE
                mErrorLoadingView.tryAgain.visibility = View.GONE
            } else {
                mErrorLoadingView.loadingBar.visibility = View.GONE
                mErrorLoadingView.errorLabel.visibility = View.GONE
                mErrorLoadingView.tryAgain.visibility = View.GONE
            }
        }

        override fun showError(messageId: Int) {
            mErrorLoadingView.errorLabel.setText(messageId)
            mErrorLoadingView.loadingBar.visibility = View.GONE
            mErrorLoadingView.errorLabel.visibility = View.VISIBLE
            mErrorLoadingView.tryAgain.visibility = View.VISIBLE
        }

        override fun showMessage(messageId: Int) {
            mErrorLoadingView.errorLabel.setText(messageId)
            mErrorLoadingView.loadingBar.visibility = View.GONE
            mErrorLoadingView.errorLabel.visibility = View.VISIBLE
            mErrorLoadingView.tryAgain.visibility = View.GONE
        }

        override fun setOnClickTryAgainListener(listener: OnClickListener) {
            mErrorLoadingView.tryAgain.setOnClickListener(listener)
        }
    }
}