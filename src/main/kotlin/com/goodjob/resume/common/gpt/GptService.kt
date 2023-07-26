package com.goodjob.resume.common.gpt

import com.fasterxml.jackson.databind.ObjectMapper
import com.goodjob.resume.common.error.JsonBindingException
import com.goodjob.resume.common.error.ThreadMalfunctionException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors


@Service
class GptService(
    private val objectMapper: ObjectMapper,
    private val chatCompletionGenerator: ChatCompletionGenerator,
) {
    private val executorService = Executors.newFixedThreadPool(1)
    private val log = LoggerFactory.getLogger(GptService::class.java)

    fun createdExpectedQuestionsAndAnswer(
        job: String,
        career: String,
        resumeData: List<ResumeRequest>
    ): WhatGeneratedQuestionResponse {
        val futures: MutableList<CompletableFuture<WhatGeneratedQuestionResponse>> = mutableListOf()

        resumeData.forEach { data ->
            val future: CompletableFuture<WhatGeneratedQuestionResponse> = CompletableFuture.supplyAsync({
                runCatching {
                    val chatMessages = PromptCreator.generateQuestionMessage(job, career, data.resumeType, data.content)
                    val chatCompletionResult = chatCompletionGenerator.generate(chatMessages)
                    val futureResult = chatCompletionResult.choices[0].message.content

                    objectMapper.readValue(futureResult, WhatGeneratedQuestionResponse::class.java)
                }.getOrElse {
                    log.error(it.message, it)
                    throw JsonBindingException()
                }
            }, executorService)
            futures.add(future)
        }

        runCatching {
            CompletableFuture.allOf(*futures.toTypedArray()).get()
        }.getOrElse {
            log.error(it.message, it)
            throw ThreadMalfunctionException()
        }

        return futures.map { it.join() }
            .filter { it.predictionResponse.isNotEmpty() }
            .fold(WhatGeneratedQuestionResponse(mutableListOf())) { acc, content ->
                acc.predictionResponse.addAll(content.predictionResponse)
                acc
            }
    }

    fun createdImprovementPointsAndAdvice(
        job: String,
        career: String,
        resumeData: List<ResumeRequest>
    ): WhatGeneratedImproveResponse {
        val futures: MutableList<CompletableFuture<WhatGeneratedImproveResponse>> = mutableListOf()

        resumeData.forEach { data ->
            val future: CompletableFuture<WhatGeneratedImproveResponse> = CompletableFuture.supplyAsync({
                runCatching {
                    val chatMessages = PromptCreator.generateAdviceMessage(job, career, data.resumeType, data.content)
                    val chatCompletionResult = chatCompletionGenerator.generate(chatMessages)
                    val futureResult = chatCompletionResult.choices[0].message.content

                    objectMapper.readValue(futureResult, WhatGeneratedImproveResponse::class.java)
                }.getOrElse {
                    log.error(it.message, it)
                    throw JsonBindingException()
                }
            }, executorService)
            futures.add(future)
        }

        runCatching {
            CompletableFuture.allOf(*futures.toTypedArray()).get()
        }.getOrElse {
            log.error(it.message, it)
            throw ThreadMalfunctionException()
        }

        return futures.map { it.join() }
            .filter { it.improvementResponse.isNotEmpty() }
            .fold(WhatGeneratedImproveResponse(mutableListOf())) { acc, content ->
                acc.improvementResponse.addAll(content.improvementResponse)
                acc
            }
    }
}

