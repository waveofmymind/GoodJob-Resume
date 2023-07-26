package com.goodjob.resume.application.facade

import com.goodjob.resume.application.out.DeletePredictionPort
import com.goodjob.resume.application.out.FindPredictionPort
import com.goodjob.resume.application.out.RegisterPredictionPort
import com.goodjob.resume.common.PredictionSnippets
import com.goodjob.resume.common.error.InvalidRequestException
import com.goodjob.resume.domain.ServiceType
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class PredictionFacadeTest : BehaviorSpec({
    val registerPredictionPort = mockk<RegisterPredictionPort>(relaxed = true)
    val findPredictionPort = mockk<FindPredictionPort>(relaxed = true)
    val deletePredictionPort = mockk<DeletePredictionPort>(relaxed = true)
    val predictionFacade = PredictionFacade(registerPredictionPort, findPredictionPort, deletePredictionPort)

    Given("예상 답변을 등록할 때") {
        val request = PredictionSnippets.registerPredictionCommand(
            listOf("title1", "title2"),
            listOf("content1", "content2")
        )

        val invalidRequest = PredictionSnippets.registerPredictionCommand(
            listOf("title1", "title2"),
            listOf("content1")
        )
        every { predictionFacade.registerPrediction(request) } returns Unit
        When("모든 정보가 정상적으로 들어왔을 때") {
            predictionFacade.registerPrediction(request)

            Then("예상 답변이 등록된다.") {
                verify(exactly = 1) { registerPredictionPort.registerPrediction(any()) }
            }
        }
        When("Title과 Content의 수가 같지 않을 때") {
            Then("예외를 반환한다.") {
                shouldThrowExactly<InvalidRequestException> {
                    predictionFacade.registerPrediction(invalidRequest)
                }
            }
        }
    }
})
