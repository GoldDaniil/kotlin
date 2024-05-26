package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(
    private val newsList: List<String>,
    private val newsDetails: List<String>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var expandedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position], newsDetails[position], position)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val detailsTextView: TextView = itemView.findViewById(R.id.detailsTextView)

        fun bind(title: String, details: String, position: Int) {
            titleTextView.text = title
            detailsTextView.text = details
            val isExpanded = position == expandedPosition
            detailsTextView.visibility = if (isExpanded) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                expandedPosition = if (isExpanded) RecyclerView.NO_POSITION else position
                notifyDataSetChanged()
            }
        }
    }
}
