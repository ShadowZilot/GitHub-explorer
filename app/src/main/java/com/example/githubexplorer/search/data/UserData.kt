package com.example.githubexplorer.search.data

import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.search.ui.UserViewHolder
import org.json.JSONObject

data class UserData(
    private val mId: Long,
    private val mName: String,
    private val mScore: Float,
    private val mAvatarLink: String,
    private val mLink: String
) : DataListItem {

    constructor(item: JSONObject) : this(
        item.getLong("id"),
        item.getString("login"),
        item.getDouble("score").toFloat(),
        item.getString("avatar_url"),
        item.getString("html_url")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mName,
        mScore,
        mAvatarLink,
        mLink
    )

    interface Mapper<T> {

        fun map(
            id: Long,
            name: String,
            score: Float,
            avatarLink: String,
            link: String
        ): T
    }

    override fun sortProperty() = mName

    override fun bindViewHolder(holder: RecyclerView.ViewHolder) {
        if (holder is UserViewHolder) {
            this.map(holder)
        }
    }

    override fun viewType() = 1
}