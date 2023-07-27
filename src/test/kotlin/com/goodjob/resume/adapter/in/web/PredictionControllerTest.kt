package com.goodjob.resume.adapter.`in`.web

import com.goodjob.resume.application.`in`.FindPredictionUseCase
import com.goodjob.resume.common.PredictionSnippets
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify


class PredictionControllerTest : DescribeSpec({
    val mockFindPredictionUseCase = mockk<FindPredictionUseCase>()
    val controller = PredictionController(mockFindPredictionUseCase)

    describe("PredictionController를 호출하면") {
        context("GET /predictions/{predictionId} 경로로 요청이 들어 왔을 때") {
            it("id에 맞는 예측을 반환한다") {
                val predictionId = 123L
                val expectedResponse = PredictionSnippets.findPredictionResponse(
                    listOf("title1", "title2"),
                    listOf("content1", "content2")
                )
                every { mockFindPredictionUseCase.findPrediction(predictionId) } returns expectedResponse

                val response = controller.findPrediction(predictionId)

                response shouldBe expectedResponse
                verify { mockFindPredictionUseCase.findPrediction(predictionId) }
            }
        }

        context("GET /predictions/{memberId} 경로로 요청이 들어 왔을 때") {
            it("멤버 id에 맞는 예측 목록을 반환한다") {
                val memberId = 456L
                val expectedResponses = listOf(
                    PredictionSnippets.findPredictionResponse(
                        listOf("title1", "title2"),
                        listOf("content1", "content2")
                    )
                )

                every { mockFindPredictionUseCase.findPredictions(memberId) } returns expectedResponses

                val response = controller.findPredictions(memberId)

                response shouldBe expectedResponses
                verify { mockFindPredictionUseCase.findPredictions(memberId) }
            }
        }
    }
})
