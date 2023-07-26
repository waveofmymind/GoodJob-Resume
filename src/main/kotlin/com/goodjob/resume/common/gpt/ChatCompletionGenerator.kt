package com.goodjob.resume.common.gpt

import com.goodjob.resume.common.config.GptConfig
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatCompletionResult
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.service.OpenAiService
import org.springframework.stereotype.Component

@Component
data class ChatCompletionGenerator(
    private val openAiService: OpenAiService
) {
    fun generate(chatMessages: List<ChatMessage>): ChatCompletionResult {
        val build = ChatCompletionRequest.builder()
            .messages(chatMessages)
            .maxTokens(GptConfig.MAX_TOKEN)
            .temperature(GptConfig.TEMPERATURE)
            .topP(GptConfig.TOP_P)
            .model(GptConfig.MODEL)
            .build()

        return openAiService.createChatCompletion(build)
    }
}
