package com.example.lutluthfi.retrofitkotlin

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.lutluthfi.retrofitkotlin.model.BeritaResponse
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.lutluthfi.retrofitkotlin.model.BeritaResponse.Beritas

class NewsAdapter(callback: Callback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private lateinit var mCallback: Callback
        private var mBeritas = ArrayList<BeritaResponse.Berita>()
    }

    init {
        mCallback = callback
    }

    fun addNews(beritas: Beritas) {
        mBeritas.addAll(beritas.datas!!)
        notifyDataSetChanged()
    }

    fun clearNews() {
        mBeritas.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun getItemCount(): Int = mBeritas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = (holder as NewsViewHolder).itemView.context
        (holder).mNewsTitleTextView.text = mBeritas[position].judul
        Glide.with(context).asBitmap().load(mBeritas[position].foto).into((holder).mNewsPosterImageView)
        (holder).mNewsContentTextView.text = mBeritas[position].konten
    }

    interface Callback {
        fun onNewsItemClick(berita: BeritaResponse.Berita)
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mNewsTitleTextView = itemView.findViewById<TextView>(R.id.tv_news_title)!!
        val mNewsPosterImageView = itemView.findViewById<ImageView>(R.id.iv_news_poster)!!
        val mNewsContentTextView = itemView.findViewById<TextView>(R.id.tv_news_content)!!

        companion object {
            fun create(parent: ViewGroup): NewsViewHolder {
                return NewsViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_content_berita, parent, false)
                )
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            NewsAdapter.mCallback.onNewsItemClick(mBeritas[adapterPosition])
        }
    }
}