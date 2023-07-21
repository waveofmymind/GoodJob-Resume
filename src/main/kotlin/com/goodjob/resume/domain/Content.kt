package com.goodjob.resume.domain

import jakarta.persistence.*

@Entity
class Content(
    @Lob
    @Column(columnDefinition = "TEXT")
    private var content: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) {
    companion object {
        @JvmStatic
        fun of(title: String): Content {
            val instance = Content()
            instance.content = title
            return instance
        }
    }
}