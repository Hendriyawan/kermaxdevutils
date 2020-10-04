package com.hdev.kermaxdevutils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_changelog.*

class ChangelogAdapter(private val changelogList : List<String>) : RecyclerView.Adapter<ChangelogAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_changelog, parent, false)
    )

    override fun getItemCount() = changelogList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(changelogList[position])
    }

    //class ViewHolder
    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(logName : String){
            item_text_view_changelog.text = logName
        }
    }
}