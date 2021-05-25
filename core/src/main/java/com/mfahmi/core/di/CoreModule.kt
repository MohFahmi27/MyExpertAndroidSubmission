package com.mfahmi.core.di

import androidx.room.Room
import com.mfahmi.core.data.MovieRepository
import com.mfahmi.core.data.source.local.LocalDataSource
import com.mfahmi.core.data.source.local.room.MovieDatabase
import com.mfahmi.core.data.source.remote.RemoteDataSource
import com.mfahmi.core.data.source.remote.network.ApiCredentials
import com.mfahmi.core.data.source.remote.network.ApiService
import com.mfahmi.core.domain.repository.MovieRepositoryInterface
import com.mfahmi.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().moviesDao() }
    single {
        Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "movie_catalogue.db")
            .fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiCredentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<MovieRepositoryInterface> { MovieRepository(get(), get(), get()) }
}