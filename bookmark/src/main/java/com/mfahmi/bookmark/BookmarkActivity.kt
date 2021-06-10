package com.mfahmi.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfahmi.bookmark.databinding.ActivityBookmarkBinding
import com.mfahmi.bookmark.di.bookmarkModule
import com.mfahmi.myexpertandroidsubmission.adapter.MoviesRecyclerviewAdapter
import com.mfahmi.myexpertandroidsubmission.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class BookmarkActivity : AppCompatActivity() {

    private val viewModel: BookmarkViewModel by viewModel()
    private val binding: ActivityBookmarkBinding by viewBinding()
    private val movieAdapter by lazy {
        MoviesRecyclerviewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieAdapter.onItemClick = { movie ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_MOVIE, movie)
                startActivity(this)
            }
        }

        loadKoinModules(bookmarkModule)
        populateView()
    }

    private fun populateView() {
        viewModel.bookmarkMovies.observe(this) {
            movieAdapter.setData(it)
            binding.lytEmptyView.root.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvMoviesBookmark) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
}