package com.goodjob.resume.adapter.out.mq

interface ConsumerAdapter {
    fun consume(message: String): String
}
