package com.mfahmi.core.data

import com.mfahmi.core.data.source.local.LocalDataSource
import com.mfahmi.core.data.source.remote.RemoteDataSource
import com.mfahmi.core.data.source.remote.network.ApiResponse
import com.mfahmi.core.data.source.remote.response.MovieResponse
import com.mfahmi.core.domain.model.Movie
import com.mfahmi.core.domain.repository.MovieRepositoryInterface
import com.mfahmi.core.utils.AppExecutors
import com.mfahmi.core.utils.DataMapper.mapDomainToEntity
import com.mfahmi.core.utils.DataMapper.mapEntitiesToDomain
import com.mfahmi.core.utils.DataMapper.mapResponsesToEntities
import com.mfahmi.core.utils.NetworkBoundResource
import com.mfahmi.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieRepositoryInterface {

    override fun getAllMoviesData(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMoviesDataFromDB().map { mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMoviesFromAPI()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val moviesList = mapResponsesToEntities(data)
                localDataSource.insertMoviesToDB(moviesList)
            }

        }.asFlow()

    override fun getBookmarkedMoviesData(): Flow<List<Movie>> =
        localDataSource.getBookmarkedMoviesFromDB().map { mapEntitiesToDomain(it) }

    override fun setBookmarkMovieFromDB(movie: Movie, state: Boolean) {
        val movieEntity = mapDomainToEntity(movie)
        appExecutors.diskIO()
            .execute { localDataSource.updateMovieFromDB(movieEntity, state) }
    }

}