package com.mfahmi.myexpertandroidsubmission.detail

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mfahmi.core.domain.model.Movie
import com.mfahmi.myexpertandroidsubmission.R
import com.mfahmi.myexpertandroidsubmission.databinding.ActivityDetailBinding
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by viewBinding()
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnBackDetail.setOnClickListener { finish() }

        val movieDetail = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        populateDetailView(movieDetail)
    }

    private fun populateDetailView(movie: Movie?) {
        movie?.let {
            with(binding) {
                imgPosterDetail.setDetailGlide(movie.posterPath)

                lytContentDetail.tvTitleDetail.text = movie.title
                lytContentDetail.tvRatingDetail.text =
                    resources.getString(R.string.rating_format, movie.voteAverage)
                lytContentDetail.tvOriginalLangDetail.text =
                    getString(
                        R.string.detail_content_format,
                        movie.originalLanguage,
                        movie.releaseDate
                    )
                lytContentDetail.tvVoteCountDetail.text = movie.voteCount.toString()
                lytContentDetail.tvOverview.text = movie.overview
                var bookmarkStatus = movie.isBookmark
                setStateButton(bookmarkStatus)

                fabBookmark.setOnClickListener {
                    bookmarkStatus = !bookmarkStatus
                    viewModel.setBookmarkMovie(movie, bookmarkStatus)
                    setStateButton(bookmarkStatus)
                    showFancyToast(bookmarkStatus)
                }
            }
        }
    }

    private fun showFancyToast(status: Boolean) {
        if (status) {
            FancyToast.makeText(
                this, getString(R.string.msg_add), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,
                false
            ).show()
        } else {
            FancyToast.makeText(
                this, getString(R.string.msg_remove), FancyToast.LENGTH_SHORT, FancyToast.WARNING,
                false
            ).show()
        }
    }

    private fun ImageView.setDetailGlide(urlPath: String) {
        Glide.with(this).load("https://image.tmdb.org/t/p/w500${urlPath}")
            .apply(RequestOptions().transform(RoundedCorners(40)))
            .into(this)
    }

    private fun setStateButton(state: Boolean) {
        with(binding) {
            if (state) fabBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_bookmark_filled
                )
            )
            else fabBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_bookmark_outlined
                )
            )
        }
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }
}