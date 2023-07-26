package com.goodjob.resume.application.`in`

import com.goodjob.resume.application.facade.FindPredictionResponse

interface FindPredictionUseCase {
    fun findPrediction(id: Long): FindPredictionResponse

    fun findPredictions(memberId: Long): List<FindPredictionResponse>
}