package com.example.githubexplorer.search.data

import androidx.recyclerview.widget.RecyclerView.ViewHolder

interface DataListItem {

    fun sortProperty() : String

    fun bindViewHolder(holder: ViewHolder)

    fun viewType() : Int
}