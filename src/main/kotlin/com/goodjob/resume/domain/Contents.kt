package com.goodjob.resume.domain

class Contents(
    private val contents: List<Content>
): List<Content> by contents {
}
