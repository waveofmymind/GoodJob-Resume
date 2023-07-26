package com.goodjob.resume.common.gpt

import jakarta.validation.constraints.NotNull

data class ResumeRequest(
    @NotNull
    val resumeType: String,
    @NotNull
    val content: String
)
