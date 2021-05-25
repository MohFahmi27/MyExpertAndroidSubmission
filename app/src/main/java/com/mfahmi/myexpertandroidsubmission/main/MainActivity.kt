package com.mfahmi.myexpertandroidsubmission.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfahmi.core.ui.MoviesRecyclerviewAdapter
import com.mfahmi.core.utils.Resource.*
import com.mfahmi.myexpertandroidsubmission.databinding.ActivityMainBinding
import com.mfahmi.myexpertandroidsubmission.detail.DetailActivity
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val binding: ActivityMainBinding by viewBinding()
    private val movieAdapter: MoviesRecyclerviewAdapter by lazy {
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

        populateView()
        binding.fabBookmarkMain.setOnClickListener {
            val uri = Uri.parse("myexpertandroidsubmission://bookmark")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    private fun populateView() {
        viewModel.movies.observe(this) {
            if (it != null) {
                when (it) {
                    is Loading -> binding.pgbMain.setVisibility(true)
                    is Success -> {
                        binding.pgbMain.setVisibility(false)
                        movieAdapter.setData(it.data)
                    }
                    is Error -> {
                        binding.pgbMain.setVisibility(false)
                        FancyToast.makeText(
                            this,
                            it.message,
                            FancyToast.LENGTH_SHORT,
                            FancyToast.WARNING,
                            false
                        ).show()
                    }
                }
            }
        }

        with(binding.rvMoviesPopular) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun ProgressBar.setVisibility(state: Boolean) {
        if (state) this.visibility = View.VISIBLE else this.visibility = View.GONE
    }
}