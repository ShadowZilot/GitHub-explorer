package com.example.githubexplorer.repository_info.data

import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.repository_info.ui.FileViewHolder
import com.example.githubexplorer.search.data.DataListItem
import org.json.JSONObject

data class FileData(
    private val mName: String,
    private val mPath: String,
    private val mUrl: String
) : DataListItem {

    constructor(item: JSONObject) : this(
        item.getString("name"),
        item.getString("path"),
        item.getString("download_url")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mName,
        mPath,
        mUrl
    )

    interface Mapper<T> {
        fun map(
            name: String,
            path: String,
            url: String
        )
    }

    override fun sortProperty() = mName

    override fun bindViewHolder(holder: RecyclerView.ViewHolder) {
        if (holder is FileViewHolder) {
            this.map(holder)
        }
    }

    override fun viewType() = 2
}