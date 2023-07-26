package com.goodjob.resume.adapter.out.mq

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumerAdapter :
    ConsumerAdapter {

    @KafkaListener(topics = [KafkaProducerAdapter.ERROR_TOPIC], groupId = "error-consumer")
    override fun consume(message: String): String {
        return message
    }

}
