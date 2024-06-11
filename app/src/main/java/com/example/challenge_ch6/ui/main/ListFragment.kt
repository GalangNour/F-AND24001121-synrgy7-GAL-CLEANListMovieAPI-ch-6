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
import com.example.challenge_ch6.databinding.FragmentListBinding
import com.example.challenge_ch6.ui.adapter.AdapterMovie
import com.example.challenge_ch6.ui.state.MovieListState
import com.example.challenge_ch6.ui.viewmodel.MovieViewModel
import com.example.challenge_ch6.ui.viewmodel.UserViewModel
import com.example.common.SharedPreferences
import com.example.domain.model.MovieDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), AdapterMovie.OnNoteItemClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var lm: LinearLayoutManager
    private lateinit var movieAdapter: AdapterMovie

    private lateinit var viewModel: MovieViewModel
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()

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

    @SuppressLint("SetTextI18n")
    private fun initView() {

        binding.toolbar.tvWelcome.text = "Welcome, ${SharedPreferences.username}"

        binding.toolbar.tvPageTitle.text = "Home"
//        binding.toolbar.tvWelcome.text = "Welcome, ${SharedPreferences.username}"
        binding.toolbar.btnFavourite.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToFavouriteFragment()
            findNavController().navigate(action)
        }
        binding.toolbar.btnUser.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUserFragment()
            findNavController().navigate(action)
        }


        lm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMoviePlayingNow.apply {
            itemAnimator = null
            layoutManager = lm
        }
    }

    private fun setObserver() {
        userViewModel.apply {
            userLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
                if (!isLoggedIn) {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onItemClick(movieDetailResponse: MovieDetail) {
        viewModel.getDetailMovie(movieDetailResponse.id)
        val action = ListFragmentDirections.actionListFragmentToDetailMovieFragment(movieDetailResponse.id)
        findNavController().navigate(action)
    }
}