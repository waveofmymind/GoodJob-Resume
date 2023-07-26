package com.goodjob.resume.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Embeddable
class Titles(
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = [jakarta.persistence.CascadeType.ALL])
    val titles: List<Title> = emptyList()
) {

    companion object {
        @JvmStatic
        fun of(modelTitles: List<String>) =
            Titles(modelTitles.map { Title.of(it) })
    }
}
