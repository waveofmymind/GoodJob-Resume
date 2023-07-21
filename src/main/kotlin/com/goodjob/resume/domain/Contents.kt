package com.goodjob.resume.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Embeddable
class Contents(
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = [jakarta.persistence.CascadeType.ALL])
    val contents: List<Content> = emptyList()
) {
    companion object {
        @JvmStatic
        fun of(modelContents: List<String>) =
            Contents(modelContents.map { Content.of(it) })
    }
}
