package com.example.data.repository

import com.example.data.database.MovieFavoriteDao
import com.example.data.database.MovieFavoriteEntity
import com.example.data.network.ApiService
import com.example.data.network.model.MovieDetailResponse
import com.example.data.network.model.MovieListResponse
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.random.Random

class MovieRepositoryImplTest {

    private lateinit var service : ApiService
    private lateinit var dao : MovieFavoriteDao
    private lateinit var repository : MovieRepositoryImpl
    private lateinit var movieList : MovieList
    private lateinit var movieDetail : MovieDetail
    private lateinit var movieListResponse : MovieListResponse
    private lateinit var movieDetailResponse: MovieDetailResponse
    private lateinit var movieFavoriteEntity: MovieFavoriteEntity
    private val movieId = Random.nextInt(1, 100)



    @Before
    fun setUp() {

        service = mockk()
        dao = mockk()
        movieList = mockk()
        movieDetail = mockk()
        movieListResponse = mockk()
        movieDetailResponse = mockk()
        movieFavoriteEntity = mockk()

        repository = MovieRepositoryImpl(service, dao)

    }

    @Test
    fun getMovieList() : Unit = runBlocking {
        val respMovieList = Response.success(movieListResponse)

        coEvery {
            movieListResponse.toDomain()
        } returns movieList

        coEvery {
            service.getMovieNowPlaying()
        } returns respMovieList

        repository.getMovieList()

        coVerify {
            service.getMovieNowPlaying()
        }
    }

    @Test
    fun getMovieDetail() : Unit = runBlocking{
        val respMovieDetail = Response.success(movieDetailResponse)

        coEvery {
            movieDetailResponse.id
        } returns movieId

        coEvery {
            movieDetailResponse.toDomain()
        } returns movieDetail

        coEvery {
            service.getMovieDetail(movieId = movieId)
        } returns respMovieDetail

        repository.getMovieDetail(movieId = movieId)

        coVerify {
            service.getMovieDetail(movieId = movieId)
        }
    }


    @Test
    fun addFavoriteMovie() : Unit = runBlocking {

        val movieDetail = MovieDetail(
            backdropPath = "backdropPath",
            id = movieId ,
            overview = "overview",
            posterPath = "posterPath",
            releaseDate = "releaseDate",
            title = "title"
        )

        val movieFavoriteEntity = MovieFavoriteEntity(
            id = movieDetail.id,
            title = movieDetail.title,
            posterPath = movieDetail.posterPath,
            overview = movieDetail.overview,
            releaseDate = movieDetail.releaseDate,
            backdropPath = movieDetail.backdropPath
        )

        coEvery {
            dao.insert(movieFavoriteEntity)
        } returns Unit

        repository.addFavoriteMovie(movieDetail)

        coVerify(exactly = 1) {
            dao.insert(match {
                it.id == movieDetail.id &&
                        it.title == movieDetail.title &&
                        it.posterPath == movieDetail.posterPath &&
                        it.overview == movieDetail.overview &&
                        it.releaseDate == movieDetail.releaseDate &&
                        it.backdropPath == movieDetail.backdropPath
            })
        }
    }


    @Test
    fun getFavoriteMovieList() : Unit = runBlocking {

        val movieFavoriteEntity = MovieFavoriteEntity(
            id = movieId,
            title = String(),
            posterPath = String(),
            overview = String(),
            releaseDate = String(),
            backdropPath = String()
        )

        val movieFavoriteEntityList = listOf(movieFavoriteEntity)

        coEvery {
            dao.getAll()
        } returns movieFavoriteEntityList

        repository.getFavoriteMovieList()

        coVerify {
            dao.getAll()
        }
    }
}