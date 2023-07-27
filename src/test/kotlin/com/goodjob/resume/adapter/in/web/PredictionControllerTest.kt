package com.goodjob.resume.adapter.`in`.web

import com.goodjob.resume.application.`in`.DeletePredictionUseCase
import com.goodjob.resume.application.`in`.FindPredictionUseCase
import com.goodjob.resume.common.PredictionSnippets
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders


class PredictionControllerTest : DescribeSpec({
    val mockFindPredictionUseCase = mockk<FindPredictionUseCase>()
    val mockDeletePredictionUseCase = mockk<DeletePredictionUseCase>()
    val controller = PredictionController(mockFindPredictionUseCase, mockDeletePredictionUseCase)
    val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    describe("PredictionController를 호출하면") {
        context("GET /predictions/{predictionId} 경로로 요청이 들어 왔을 때") {
            it("id에 맞는 예측을 반환한다") {
                val predictionId = 1L
                val expectedResponse = PredictionSnippets.findPredictionResponse()
                every { mockFindPredictionUseCase.findPrediction(predictionId) } returns expectedResponse

                mockMvc.perform(
                    MockMvcRequestBuilders.get("/predictions/$predictionId")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isOk)
                verify(exactly = 1) { mockFindPredictionUseCase.findPrediction(predictionId) }
            }
        }

        context("GET /predictions/{memberId}/result 경로로 요청이 들어 왔을 때") {
            it("멤버 id에 맞는 예측 목록을 반환한다") {
                val memberId = 4L
                val expectedResponses = listOf(
                    PredictionSnippets.findPredictionResponse()
                )

                every { mockFindPredictionUseCase.findPredictions(memberId) } returns expectedResponses

                mockMvc.perform(
                    MockMvcRequestBuilders.get("/predictions/$memberId/result")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isOk)
                verify(exactly = 1) { mockFindPredictionUseCase.findPredictions(memberId) }
            }
        }

        context("DELETE /predictions/{predictionId} 경로로 요청이 들어 왔을 때") {
            it("id에 맞는 예측을 삭제한다") {
                val predictionId = 4L
                every { mockDeletePredictionUseCase.deletePrediction(predictionId) } returns Unit

                mockMvc.perform(
                    MockMvcRequestBuilders.delete("/predictions/$predictionId")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk)
                verify(exactly = 1) { mockDeletePredictionUseCase.deletePrediction(predictionId) }
            }
        }
    }
})
