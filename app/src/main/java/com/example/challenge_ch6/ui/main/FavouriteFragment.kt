@file:Suppress(
    "ReplaceGetOrSet"
)

package com.example.challenge_ch6.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_ch6.databinding.FragmentFavouriteBinding
import com.example.challenge_ch6.ui.adapter.AdapterMovie
import com.example.challenge_ch6.ui.state.FavouriteMovieListState
import com.example.challenge_ch6.ui.viewmodel.MovieViewModel
import com.example.domain.model.MovieDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(),AdapterMovie.OnNoteItemClickListener {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var lm: LinearLayoutManager
    private lateinit var movieAdapter: AdapterMovie

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.getFavouriteMovies()
        viewModel.movieListFavouriteState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavouriteMovieListState.Success -> {
                    movieAdapter = AdapterMovie(state.movies, this)
                    binding.rvMoviePlayingNow.adapter = movieAdapter
                }

                is FavouriteMovieListState.Error -> {}
                is FavouriteMovieListState.Loading -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.toolbar.btnFavourite.visibility = View.GONE
        binding.toolbar.btnUser.visibility = View.GONE
        binding.toolbar.tvPageTitle.text = "Favourite"
        binding.toolbar.tvWelcome.visibility = View.GONE

        lm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMoviePlayingNow.apply {
            itemAnimator = null
            layoutManager = lm
        }
    }

    override fun onItemClick(movieDetail: MovieDetail) {
        viewModel.getDetailMovie(movieDetail.id)
        val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailMovieFragment(movieDetail.id)
        findNavController().navigate(action)

    }
}