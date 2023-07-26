package com.goodjob.resume.adapter.out.mq

interface ProducerAdapter {

    fun sendError(message: String?)
}
