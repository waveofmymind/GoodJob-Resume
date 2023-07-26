package com.goodjob.resume.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "prediction")
class Prediction(

    private val memberId: Long,

    @Enumerated(EnumType.STRING)
    val serviceType: ServiceType,

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "prediction")
    val titles: MutableList<Title> = mutableListOf(),

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "prediction")
    val contents: MutableList<Content> = mutableListOf(),

    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L
) {
    val getMember: Long
        get() = memberId

    fun addTitle(titles: List<String>) {
        titles.forEach { title ->
            val newTitle = Title(title, this)
            this.titles.add(newTitle)
        }
    }

    fun addContent(contents: List<String>) {
        contents.forEach { content ->
            val newContent = Content(content, this)
            this.contents.add(newContent)
        }
    }

}
