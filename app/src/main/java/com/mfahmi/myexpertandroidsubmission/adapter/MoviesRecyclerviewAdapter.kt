package com.mfahmi.myexpertandroidsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mfahmi.core.domain.model.Movie
import com.mfahmi.myexpertandroidsubmission.R
import com.mfahmi.myexpertandroidsubmission.databinding.ItemsDataLayoutBinding

class MoviesRecyclerviewAdapter :
    RecyclerView.Adapter<MoviesRecyclerviewAdapter.MainRecyclerviewViewHolder>() {

    private val listItems = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(listData: List<Movie>?) {
        if (listData == null) return
        listItems.clear()
        listItems.addAll(listData)
        notifyDataSetChanged()
    }

    inner class MainRecyclerviewViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemsDataLayoutBinding.bind(itemView)
        fun bind(movies: Movie) {
            with(binding) {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500/${movies.posterPath}")
                    .into(imgPoster)
                tvTitle.text = movies.title
                tvRating.text =
                    itemView.context.getString(R.string.rating_format, movies.voteAverage)
                tvOverviewItems.text = itemView.context.getString(
                    R.string.overview_format,
                    movies.overview.substring(0, 25)
                )
            }
        }

        init {
            binding.root.setOnClickListener { onItemClick?.invoke(listItems[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainRecyclerviewViewHolder = MainRecyclerviewViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.items_data_layout, parent, false)
    )

    override fun onBindViewHolder(holder: MainRecyclerviewViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int = listItems.size
}