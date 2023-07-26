package com.goodjob.resume.application.out

import com.goodjob.resume.domain.Prediction


interface FindPredictionPort {

    fun findPredictionById(id: Long?): Prediction

    fun findPredictionsByMemberId(memberId: Long): List<Prediction>

}