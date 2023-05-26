package com.example.githubexplorer.search.ui

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.UserItemBinding
import com.example.githubexplorer.search.data.UserData
import com.squareup.picasso.Picasso


class UserViewHolder(
    private val mBinding: UserItemBinding
) : ViewHolder(mBinding.root), UserData.Mapper<Unit> {

    override fun map(id: Long, name: String, score: Float, avatarLink: String, link: String) {
        Picasso.get()
            .load(avatarLink)
            .error(R.drawable.baseline_person_24)
            .into(mBinding.userAvatar)
        mBinding.userName.text = name
        mBinding.scoreTitle.text = score.toString()
        mBinding.root.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            mBinding.root.context.startActivity(browserIntent)
        }
    }
}