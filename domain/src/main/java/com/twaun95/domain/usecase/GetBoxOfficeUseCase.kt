package com.twaun95.domain.usecase

import com.twaun95.domain.model.entity.BoxOfficeEntity
import com.twaun95.domain.model.Result
import com.twaun95.domain.repository.MovieRepository
import javax.inject.Inject

class GetBoxOfficeUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(
        date: String
    ): Result<List<BoxOfficeEntity>> {
        return movieRepository.getBoxOffice(date)
    }
}