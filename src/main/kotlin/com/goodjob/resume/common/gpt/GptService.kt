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

    fun generateQuestionMessage(job: String, career: String, resumeType: String, content: String): List<ChatMessage> {
        val prompt = Prompt.generateQuestionPrompt(job, career, resumeType)
        log.debug("생성된 프롬프트 : {}", prompt)

        val systemMessage = ChatMessage(ChatMessageRole.SYSTEM.value(), prompt)
        val userMessage = ChatMessage(ChatMessageRole.USER.value(), content)

        return listOf(systemMessage, userMessage)
    }

    fun generateAdviceMessage(job: String, career: String, resumeType: String, content: String): List<ChatMessage> {
        val prompt = Prompt.generateAdvicePrompt(job, career, resumeType)
        log.debug("생성된 프롬프트 : {}", prompt)

        val systemMessage = ChatMessage(ChatMessageRole.SYSTEM.value(), prompt)
        val userMessage = ChatMessage(ChatMessageRole.USER.value(), content)

        return listOf(systemMessage, userMessage)
    }

    @Async
    fun createdExpectedQuestionsAndAnswer(
        job: String,
        career: String,
        resumeData: List<ResumeRequest>
    ): CompletableFuture<WhatGeneratedQuestionResponse> {
        val futures = resumeData.map { data ->
            CompletableFuture.supplyAsync({
                val chatMessages = generateQuestionMessage(job, career, data.resumeType, data.content)

                runCatching {
                    val chatCompletionResult = chatCompletionGenerator.generate(chatMessages)
                    val futureResult = chatCompletionResult.choices[0].message.content

                    objectMapper.readValue<WhatGeneratedQuestionResponse>(futureResult)
                }.getOrElse {
                    log.error(it.message, it)
                    throw JsonBindingException()
                }
            }, executorService)
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        val results = futures.map { it.join() }
        val combinedPredictionResponse = results.flatMap { it.predictionResponse }
        val result = WhatGeneratedQuestionResponse(combinedPredictionResponse)

        return CompletableFuture.completedFuture(result)
    }

    @Async
    fun createdImprovementPointsAndAdvice(
        job: String,
        career: String,
        resumeData: List<ResumeRequest>
    ): CompletableFuture<WhatGeneratedImproveResponse> {
        val futures = resumeData.map { data ->
            CompletableFuture.supplyAsync({
                val chatMessages = generateAdviceMessage(job, career, data.resumeType, data.content)

                runCatching {
                    val chatCompletionResult = chatCompletionGenerator.generate(chatMessages)
                    val futureResult = chatCompletionResult.choices[0].message.content

                    objectMapper.readValue<WhatGeneratedImproveResponse>(futureResult)
                }.getOrElse {
                    log.error(it.message)
                    throw JsonBindingException()
                }
            }, executorService)
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        val results = futures.map { it.join() }
        val combinedImprovementResponses = results.flatMap { it.improvementResponse }
        val result = WhatGeneratedImproveResponse(combinedImprovementResponses)

        return CompletableFuture.completedFuture(result)
    }
}
