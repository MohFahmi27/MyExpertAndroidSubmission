package com.mfahmi.core.di

import androidx.room.Room
import com.mfahmi.core.data.MovieRepository
import com.mfahmi.core.data.source.local.LocalDataSource
import com.mfahmi.core.data.source.local.room.MovieDatabase
import com.mfahmi.core.data.source.remote.RemoteDataSource
import com.mfahmi.core.data.source.remote.network.ApiCredentials.BASE_URL
import com.mfahmi.core.data.source.remote.network.ApiService
import com.mfahmi.core.domain.repository.MovieRepositoryInterface
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase = SQLiteDatabase.getBytes("mohfahmi".toCharArray())
        val supportFactory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "movie_catalogue.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(supportFactory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<MovieRepositoryInterface> { MovieRepository(get(), get()) }
}