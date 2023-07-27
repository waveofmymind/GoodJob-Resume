package com.goodjob.resume.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*


@Entity
class Title(
    @Lob
    @Column(columnDefinition = "TEXT") var title: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prediction_id")
    @JsonIgnore
    val prediction: Prediction,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) {

    companion object {
        fun of(title: String, prediction: Prediction) =
            Title(title, prediction)
    }
}
