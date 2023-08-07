package com.example.fypfsy.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fypfsy.R
import kotlinx.android.synthetic.main.activity_video_views.*

class VideoAdapter(private val videoList: List<VideoItem>) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
        private var itemClickListener: ((VideoItem) -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_video_views, parent, false)
            return VideoViewHolder(view)
        }

        override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
            val videoItem = videoList[position]
            holder.titleTextView.text = videoItem.title

            holder.itemView.setOnClickListener {
                itemClickListener?.invoke(videoItem)
            }
        }

        fun setOnItemClickListener(listener: (VideoItem) -> Unit) {
            itemClickListener = listener
        }

        override fun getItemCount(): Int = videoList.size

        inner class VideoViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.textView3)
        }
    }

