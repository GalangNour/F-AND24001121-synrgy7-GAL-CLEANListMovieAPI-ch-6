package com.example.challenge_ch6.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_ch6.databinding.FragmentListBinding
import com.example.challenge_ch6.ui.adapter.AdapterMovie
import com.example.domain.model.MovieDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), AdapterMovie.OnNoteItemClickListener {

    private var _binding: FragmentListBinding? = null
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
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        viewModel.fetchMoviePlayingNow()

        // Observe movieListState LiveData
        viewModel.movieListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieListState.Success -> {
                    movieAdapter = AdapterMovie(state.movies.results, this)
                    binding.rvMoviePlayingNow.adapter = movieAdapter
                }

                is MovieListState.Error -> { }
                is MovieListState.Loading -> { }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView() {
        lm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMoviePlayingNow.apply {
            itemAnimator = null
            layoutManager = lm
        }
    }

    override fun onItemClick(movieDetailResponse: MovieDetail) {
        viewModel.getDetailMovie(movieDetailResponse.id)
        val action = ListFragmentDirections.actionListFragmentToDetailMovieFragment(movieDetailResponse.id)
        findNavController().navigate(action)
    }
}