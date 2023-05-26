package com.example.githubexplorer.repository_info.data

import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.repository_info.ui.DirViewHolder
import com.example.githubexplorer.search.data.DataListItem
import org.json.JSONObject

data class DirData(
    private val mName: String,
    private val mPath: String,
) : DataListItem {

    constructor(item: JSONObject) : this(
        item.getString("name"),
        item.getString("path")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mName,
        mPath
    )

    interface Mapper<T> {
        fun map(
            name: String,
            path: String
        ) : T
    }

    override fun sortProperty() = mName

    override fun bindViewHolder(holder: RecyclerView.ViewHolder) {
        if (holder is DirViewHolder) {
            this.map(holder)
        }
    }

    override fun viewType() = 1
}