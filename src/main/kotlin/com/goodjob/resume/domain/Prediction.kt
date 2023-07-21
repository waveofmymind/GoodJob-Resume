package com.goodjob.resume.domain

import jakarta.persistence.*

@Entity
@Table(name = "prediction")
class Prediction(

    @Enumerated(EnumType.STRING)
    private val serviceType: ServiceType,

    private val memberId: Long,

    @Embedded
    val titles: Titles = Titles(),

    @Embedded
    val contents: Contents = Contents(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) {

}
