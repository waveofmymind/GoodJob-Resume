package com.goodjob.resume.domain

import jakarta.persistence.*


@Entity
class Title(
    @Lob
    @Column(columnDefinition = "TEXT") var title: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) {

    companion object {
        fun of(title: String): Title {
            val instance = Title()
            instance.title = title
            return instance
        }
    }
}