package com.example.customviews

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView



class TextAdapter(val layoutId: Int = R.layout.item_text, val viewId: Int = R.id.text, var items: List<String> = emptyList(), val layoutManager: LinearLayoutManager): RecyclerView.Adapter<TextAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        val height = parent.measuredHeight / 3
        itemView.minimumHeight = height
        itemView.layoutParams.height = height

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size + 2
    }

    override fun onBindViewHolder(holder: TextAdapter.ViewHolder, position: Int) {
        if (items.isEmpty()) return
        if (position <= 0 || position > items.size) {
            holder.textView.text = ""
            holder.itemView.setOnClickListener(null)
        } else {
            holder.textView.text = items[position - 1]
            holder.itemView.setOnClickListener {
                layoutManager.scrollToPositionWithOffset(position - 1, 0)
            }
        }
    }
}