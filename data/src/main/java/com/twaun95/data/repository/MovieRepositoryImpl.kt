package com.twaun95.data.repository

import com.twaun95.data.model.APIKey
import com.twaun95.data.model.boxoffice.DailyBoxOffice
import com.twaun95.data.model.info.MovieInfo
import com.twaun95.data.service.MovieService
import com.twaun95.domain.model.entity.BoxOfficeEntity
import com.twaun95.domain.model.entity.MovieEntity
import com.twaun95.domain.model.Result
import com.twaun95.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.Exception

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val apiKey: APIKey
) : MovieRepository {
    override suspend fun getBoxOffice(date: String) : Result<List<BoxOfficeEntity>> {
        val response = movieService.getBoxOffice(apiKey.key, date)

        return try {
            if (response.isSuccessful) {
                return Result.Success(response.body()!!.boxOfficeResult.dailyBoxOfficeList!!.map {
                    DailyBoxOffice.toBoxOfficeEntity(it)
                })
            } else {
                Result.Fail(IllegalArgumentException("일일 박스 오피스 불러오기 실패."))
            }
        } catch (e: Exception) {
            Result.Fail(e)
        }
    }

    override suspend fun getMovieInfo(code: String): Result<MovieEntity> {
        val response = movieService.getMovieInfo(apiKey.key, code)

        return try {
            if (response.isSuccessful) {
                return Result.Success(response.body()!!.movieInfoResult.movieInfo.run {
                    MovieInfo.toMovieEntity(this)
                })
            } else {
                Result.Fail(IllegalArgumentException("영화 정보 불러오기 실패."))
            }
        } catch (e: Exception) {
            Result.Fail(e)
        }
    }
}