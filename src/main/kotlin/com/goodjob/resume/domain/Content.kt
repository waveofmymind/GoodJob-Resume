package com.goodjob.resume.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Content(
    @Lob
    @Column(columnDefinition = "TEXT") var content: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prediction_id")
    @JsonIgnore
    val prediction: Prediction,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) {
    companion object {
        fun of(content: String, prediction: Prediction) =
            Content(content, prediction)
    }
}
