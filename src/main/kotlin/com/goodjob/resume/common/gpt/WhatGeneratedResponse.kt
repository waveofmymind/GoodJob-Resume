package com.goodjob.resume.common.gpt

import com.goodjob.resume.application.facade.RegisterPredictionCommand
import com.goodjob.resume.domain.Contents
import com.goodjob.resume.domain.ServiceType
import com.goodjob.resume.domain.Titles

data class WhatGeneratedQuestionResponse(
    val predictionResponse: MutableList<PredictionResponse>
) {
    fun toCommand(memberId: Long): RegisterPredictionCommand {
        val titleList = predictionResponse.map { it.question }
        val contentList = predictionResponse.map { it.bestAnswer }

        val titles = Titles.of(titleList)
        val contents = Contents.of(contentList)

        return RegisterPredictionCommand(
            memberId = memberId,
            titles = titles,
            contents = contents,
            serviceType = ServiceType.EXPECTED_QUESTION
        )
    }
}

data class WhatGeneratedImproveResponse(
    val improvementResponse: MutableList<ImprovementResponse>
) {
    fun toCommand(memberId: Long): RegisterPredictionCommand {
        val titleList = improvementResponse.map { it.improvementPoint }
        val contentList = improvementResponse.map { it.advice }

        val titles = Titles.of(titleList)
        val contents = Contents.of(contentList)

        return RegisterPredictionCommand(
            memberId = memberId,
            titles = titles,
            contents = contents,
            serviceType = ServiceType.EXPECTED_ADVICE
        )
    }
}

data class PredictionResponse(
    val question: String,
    val bestAnswer: String
)

data class ImprovementResponse(
    val improvementPoint: String,
    val advice: String
)
