package com.goodjob.resume.application.`in`

import com.goodjob.resume.application.facade.RegisterPredictionCommand

interface RegisterPredictionUseCase {
    fun registerPrediction(command : RegisterPredictionCommand)
}