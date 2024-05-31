package com.example.data.network.di

import android.content.Context
import androidx.room.Room
import com.example.common.Constant
import com.example.data.database.AppDatabase
import com.example.data.database.MovieFavoriteDao
import com.example.data.database.UserDao
import com.example.data.network.ApiService
import com.example.data.repository.MovieRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideMovieFavoriteDao(appDatabase: AppDatabase): MovieFavoriteDao {
        return appDatabase.movieFavoriteDao()
    }

//    @Provides
//    fun provideMovieRepository(apiService: ApiService): MovieRepository {
//        return MovieRepository(apiService = apiService)
//    }

    @Provides
    fun provideGetMovieRepository(apiService: ApiService, movieFavoriteDao: MovieFavoriteDao) :  MovieRepository{
        return MovieRepositoryImpl(apiService = apiService, movieDao = movieFavoriteDao)
    }
    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao = userDao)
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }



}