package com.example.githubexplorer.search.data

import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.search.ui.RepositoryViewHolder
import org.json.JSONObject

data class RepositoryData(
    private val mId: Long,
    private val mName: String,
    private val mOwner: String,
    private val mLink: String,
    private val mForksCount: Int,
    private val mDescription: String
) : DataListItem {

    constructor(item: JSONObject) : this(
        item.getLong("id"),
        item.getString("name"),
        item.getJSONObject("owner").getString("login"),
        item.getString("html_url"),
        item.getInt("forks_count"),
        item.getString("description")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mName,
        mOwner,
        mLink,
        mForksCount,
        mDescription,
    )

    interface Mapper<T> {
        fun map(
            id: Long,
            name: String,
            owner: String,
            link: String,
            forksCount: Int,
            description: String
        ): T
    }

    override fun sortProperty() = mName

    override fun bindViewHolder(holder: RecyclerView.ViewHolder) {
        if (holder is RepositoryViewHolder) {
            this.map(holder)
        }
    }

    override fun viewType() = 2
}