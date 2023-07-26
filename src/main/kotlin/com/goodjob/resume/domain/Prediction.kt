package com.goodjob.resume.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "prediction")
class Prediction(


    private val memberId: Long,

    @Enumerated(EnumType.STRING) val serviceType: ServiceType,

    @Embedded
    val titles: Titles = Titles(),

    @Embedded
    val contents: Contents = Contents(),

    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L
) {
    val getMember: Long
        get() = memberId

}
