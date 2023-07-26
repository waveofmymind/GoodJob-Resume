package com.goodjob.resume.application.facade

import com.goodjob.resume.common.gpt.ResumeRequest

data class CreatePromptRequest(
    val memberId: Long,
    val job: String,
    val career: String,
    val resumeRequests: List<ResumeRequest>
)
