package com.mfahmi.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mfahmi.core.R
import com.mfahmi.core.databinding.ItemsDataLayoutBinding
import com.mfahmi.core.domain.model.Movie

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
                imgPoster.setRoundedGlide(movies.posterPath)
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
        holder.itemView.setAnimationRecyclerView()
    }

    override fun getItemCount(): Int = listItems.size

    private fun ImageView.setRoundedGlide(urlPath: String) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/$urlPath")
            .apply(RequestOptions().override(140, 180)).apply(
                RequestOptions()
                    .transform(RoundedCorners(15))
            ).into(this)
    }

    private fun View.setAnimationRecyclerView() {
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.recyclerview_anim_items))
    }
}