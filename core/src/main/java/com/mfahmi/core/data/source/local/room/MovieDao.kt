package com.mfahmi.core.data.source.local.room

import androidx.room.*
import com.mfahmi.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM tb_movie")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tb_movie WHERE is_bookmark = 1")
    fun getBookmarkedMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieEntities: List<MovieEntity>)

    @Update
    suspend fun updateBookmarkMovie(movieEntity: MovieEntity)
}