package com.goodjob.resume.common.gpt

import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import org.slf4j.LoggerFactory

object PromptCreator {

    private val log = LoggerFactory.getLogger(PromptCreator::class.java)

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
}
