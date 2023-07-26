package com.goodjob.resume.common.gpt

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.Async
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.goodjob.resume.common.config.GptConfig
import com.goodjob.resume.common.error.JsonBindingException
import com.goodjob.resume.common.error.PredictionCreateException
import com.goodjob.resume.common.error.ThreadMalfunctionException
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatCompletionResult
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@Service
@EnableAsync
class GptService(
    private val openAiService: OpenAiService,
    private val objectMapper: ObjectMapper,
    private val chatCompletionGenerator: ChatCompletionGenerator,
) {
    private val executorService = Executors.newFixedThreadPool(1)
    private val log = LoggerFactory.getLogger(GptService::class.java)

    fun createdExpectedQuestionAndAnswer(
        job: String,
        career: String,
        resumeData: List<ResumeRequest>
    ): WhatGeneratedQuestionResponse {

        val response: MutableList<CompletableFuture<WhatGeneratedQuestionResponse>> = mutableListOf()

        resumeData.forEach {
            val future: CompletableFuture<WhatGeneratedQuestionResponse> = CompletableFuture.supplyAsync {
                PromptCreator.generateQuestionMessage(job, career, it.resumeType, it.content)
                    .let { chatMessages ->
                        runCatching {
                            val chatCompletionResult = chatCompletionGenerator.generate(chatMessages)
                            val futureResult = chatCompletionResult.choices[0].message.content

                            objectMapper.readValue<WhatGeneratedQuestionResponse>(futureResult)
                        }.getOrElse {
                            log.error(it.message)
                            throw JsonBindingException()
                        }
                    }
            }
            response.add(future)
        }

        val allFutures: CompletableFuture<Void> = CompletableFuture.allOf(*response.toTypedArray())
        runCatching {
            allFutures.get()
        }.getOrElse {
            log.error(it.message, it)
            throw ThreadMalfunctionException()
        }

        return response.map { it.join() }
            .filter { it.predictionResponse.isNotEmpty() }
            .fold(WhatGeneratedQuestionResponse(mutableListOf())) { acc, content ->
                acc.predictionResponse.addAll(content.predictionResponse)
                acc
            }
    }

    @Async
    fun createdImprovementPointsAndAdvice(
        job: String,
        career: String,
        resumeData: List<ResumeRequest>
    ): WhatGeneratedImproveResponse {
        val response: MutableList<CompletableFuture<WhatGeneratedImproveResponse>> = mutableListOf()

        resumeData.forEach {
            val future: CompletableFuture<WhatGeneratedImproveResponse> = CompletableFuture.supplyAsync {
                PromptCreator.generateAdviceMessage(job, career, it.resumeType, it.content)
                    .let { chatMessages ->
                        runCatching {
                            val chatCompletionResult = chatCompletionGenerator.generate(chatMessages)
                            val futureResult = chatCompletionResult.choices[0].message.content

                            objectMapper.readValue<WhatGeneratedImproveResponse>(futureResult)
                        }.getOrElse {
                            log.error(it.message)
                            throw JsonBindingException()
                        }
                    }
            }
            response.add(future)
        }

        val allFutures: CompletableFuture<Void> = CompletableFuture.allOf(*response.toTypedArray())
        runCatching {
            allFutures.get()
        }.getOrElse {
            log.error(it.message, it)
            throw ThreadMalfunctionException()
        }

        return response.map { it.join() }
            .filter { it.improvementResponse.isNotEmpty() }
            .fold(WhatGeneratedImproveResponse(mutableListOf())) { acc, content ->
                acc.improvementResponse.addAll(content.improvementResponse)
                acc
            }
    }
}
