package com.goodjob.resume.application.facade

import com.goodjob.resume.domain.Contents
import com.goodjob.resume.domain.Prediction
import com.goodjob.resume.domain.ServiceType
import com.goodjob.resume.domain.Titles

data class RegisterPredictionCommand(
    val memberId: Long,
    val titles: Titles,
    val contents: Contents,
    val serviceType: ServiceType
) {
    fun toEntity() = Prediction(
        memberId = memberId,
        titles = titles,
        contents = contents,
        serviceType = serviceType
    )
}
