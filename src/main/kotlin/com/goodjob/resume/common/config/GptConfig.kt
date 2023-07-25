package com.goodjob.resume.common.config

import com.theokanning.openai.service.OpenAiService
import jdk.jfr.internal.management.StreamManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import java.time.Duration


class GptConfig {

    @Value("\${openai.token}")
    private lateinit var token: String

    @Bean
    fun openAiService(): OpenAiService {
        return OpenAiService(token, TIME_OUT)
    }

    companion object {
        const val MODEL = "gpt-3.5-turbo"
        const val TOP_P = 1.0
        const val MAX_TOKEN = 2000
        const val TEMPERATURE = 1.0
        val TIME_OUT = Duration.ofSeconds(300)
    }
}