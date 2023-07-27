package com.goodjob.resume.common.gpt


import com.goodjob.resume.common.PredictionSnippets
import com.goodjob.resume.common.config.GptConfig
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatCompletionResult
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.service.OpenAiService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ChatCompletionGeneratorTest : BehaviorSpec({
    val mockOpenAiService = mockk<OpenAiService>()
    val generator = ChatCompletionGenerator(mockOpenAiService)

    Given("채팅 메시지와 설정이 주어졌을 때") {
        val chatMessages = PredictionSnippets.chatMessages()
        val build = ChatCompletionRequest.builder()
            .messages(chatMessages)
            .maxTokens(GptConfig.MAX_TOKEN)
            .temperature(GptConfig.TEMPERATURE)
            .topP(GptConfig.TOP_P)
            .model(GptConfig.MODEL)
            .build()
        val expectedResult = ChatCompletionResult()

        every { mockOpenAiService.createChatCompletion(build) } returns expectedResult

        When("generate 메소드가 호출되면") {
            val result = generator.generate(chatMessages)

            Then("올바른 결과가 반환되어야 한다") {
                result shouldBe expectedResult
            }

            And("createChatCompletion 메소드가 올바른 파라미터로 호출되어야 한다") {
                verify { mockOpenAiService.createChatCompletion(build) }
            }
        }
    }
})
