package com.goodjob.resume.common

import com.goodjob.resume.application.facade.RegisterPredictionCommand
import com.goodjob.resume.domain.ServiceType

object PredictionSnippets {

    fun registerPredictionCommand(titleList: List<String>, contentList: List<String>) =
        RegisterPredictionCommand(
            memberId = 1L,
            titles = titleList,
            contents = contentList,
            serviceType = ServiceType.EXPECTED_ADVICE
        )
}