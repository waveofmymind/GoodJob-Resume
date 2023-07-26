package com.goodjob.resume.application.facade

import com.goodjob.resume.domain.Contents
import com.goodjob.resume.domain.Prediction
import com.goodjob.resume.domain.ServiceType
import com.goodjob.resume.domain.Titles
import java.time.LocalDateTime

data class FindPredictionResponse(
    val id: Long,
    val titles: Titles,
    val contents: Contents,
    val serviceType: ServiceType,
    val createdDate: LocalDateTime
) {
    companion object {
        @JvmStatic
        fun toResponse(prediction: Prediction): FindPredictionResponse {
            return FindPredictionResponse(
                id = prediction.id,
                titles = prediction.titles,
                contents = prediction.contents,
                serviceType = prediction.serviceType,
                createdDate = prediction.createdDate
            )
        }
    }
}
