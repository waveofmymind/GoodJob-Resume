package com.goodjob.resume.application.out

import com.goodjob.resume.domain.Prediction

interface RegisterPredictionPort {
    fun registerPrediction(prediction: Prediction)
}