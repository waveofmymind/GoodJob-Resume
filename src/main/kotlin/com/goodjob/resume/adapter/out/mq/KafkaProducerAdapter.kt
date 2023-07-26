package com.goodjob.resume.adapter.out.mq

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducerAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>
) : ProducerAdapter {

    override fun sendError(message: String?) {
        kafkaTemplate.send(ERROR_TOPIC, message)
    }

    companion object {
        const val ERROR_TOPIC = "error-prod"
    }
}