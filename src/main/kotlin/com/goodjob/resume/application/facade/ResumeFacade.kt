package com.goodjob.resume.application.facade

import com.fasterxml.jackson.databind.ObjectMapper
import com.goodjob.resume.adapter.out.mq.ProducerAdapter
import com.goodjob.resume.application.`in`.RegisterPredictionUseCase
import com.goodjob.resume.common.error.PredictionCreateException
import com.goodjob.resume.common.gpt.GptService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ResumeFacade(
    private val gptService: GptService,
    private val objectMapper: ObjectMapper,
    private val registerPredictionUseCase: RegisterPredictionUseCase,
    private val producerAdapter: ProducerAdapter
) {
    @KafkaListener(topics = ["question-local"], groupId = "gptgroup")
    fun generatedQuestionResponseWithKafka(message: String) {
        try {
            val request = objectMapper.readValue(message, CreatePromptRequest::class.java)

            val response = gptService.createdExpectedQuestionAndAnswer(
                request.job, request.career, request.resumeRequests
            )

            registerPredictionUseCase.registerPrediction(response.toCommand(request.memberId))
        } catch (e: Exception) {
            val request = objectMapper.readValue(message, CreatePromptRequest::class.java)
            producerAdapter.sendError(request.memberId.toString())
            throw PredictionCreateException()
        }
    }

    @KafkaListener(topics = ["advice-local"], groupId = "gptgroup")
    fun generateAdviceWithKafka(message: ConsumerRecord<String, String>) {
        try {
            val request = objectMapper.readValue(message.value(), CreatePromptRequest::class.java)

            request.memberId.let {
                val response = gptService.createdImprovementPointsAndAdvice(
                    request.job, request.career, request.resumeRequests
                )
                registerPredictionUseCase.registerPrediction(response.toCommand(request.memberId))
            }

        } catch (e: Exception) {
            val request = objectMapper.readValue(message.value(), CreatePromptRequest::class.java)
            producerAdapter.sendError(request.memberId.toString())
            throw PredictionCreateException()
        }
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(ResumeFacade::class.java)
    }
}
