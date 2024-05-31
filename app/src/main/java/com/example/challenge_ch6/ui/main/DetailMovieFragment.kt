package com.example.challenge_ch6.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.challenge_ch6.databinding.FragmentDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private var movieId: Int = 0
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = DetailMovieFragmentArgs.fromBundle(it)
            movieId = args.movieId
        }

        viewModel.getDetailMovie(movieId)

        viewModel.movieDetailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieDetailState.Success -> {

                    binding.tvTitle.text = state.moviesDetail.title
                    binding.tvOverview.text = state.moviesDetail.overview
                    Glide.with(binding.imgMovie)
                        .load("https://image.tmdb.org/t/p/w500${state.moviesDetail.posterPath}")
                        .into(binding.imgMovie)

                    binding.fabFavourite.setOnClickListener {
                        viewModel.addFavouriteMovie(state.moviesDetail)
                        Toast.makeText(requireContext(), "${state.moviesDetail.title} is added to your Favourite", Toast.LENGTH_SHORT).show()
                    }
                }

                is MovieDetailState.Error -> {}
                is MovieDetailState.Loading -> {}
            }
        }


    }

}