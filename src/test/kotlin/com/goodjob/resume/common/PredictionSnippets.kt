package com.goodjob.resume.common

import com.goodjob.resume.application.facade.FindPredictionResponse
import com.goodjob.resume.application.facade.RegisterPredictionCommand
import com.goodjob.resume.common.gpt.PromptCreator
import com.goodjob.resume.domain.*
import java.time.LocalDateTime

object PredictionSnippets {

    private fun prediction() : Prediction {
        val prediction = Prediction(
            memberId = 1L,
            serviceType = ServiceType.EXPECTED_ADVICE
        )
        prediction.addTitle(listOf("title1", "title2"))
        prediction.addContent(listOf("content1", "content2"))
        return prediction
    }
    fun registerPredictionCommand(titleList: List<String>, contentList: List<String>) =
        RegisterPredictionCommand(
            memberId = 1L,
            titles = titleList,
            contents = contentList,
            serviceType = ServiceType.EXPECTED_ADVICE
        )

    fun findPredictionResponse() =
        FindPredictionResponse(
            id = 1L,
            titles(),
            contents(),
            serviceType = ServiceType.EXPECTED_ADVICE,
            createdDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
        )

    fun chatMessages() =
        PromptCreator.generateQuestionMessage(
            job = "job",
            career = "career",
            resumeType = "resumeType",
            content = "content"
        )
    private fun titles() =
        Titles(
            listOf(
                Title.of("title1", prediction()),
            )
        )
    private fun contents() =
        Contents(
            listOf(
                Content.of("content1", prediction()),
            )
        )
}
