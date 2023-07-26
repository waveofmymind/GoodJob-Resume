package com.goodjob.resume.application.facade

import com.goodjob.resume.domain.*

data class RegisterPredictionCommand(
    val memberId: Long,
    val titles: List<String>,
    val contents: List<String>,
    val serviceType: ServiceType
) {
    fun toEntity() =
        Prediction(
            memberId = memberId,
            serviceType = serviceType
        )

}
