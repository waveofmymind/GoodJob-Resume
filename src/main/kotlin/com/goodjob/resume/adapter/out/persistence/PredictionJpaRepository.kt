package com.goodjob.resume.adapter.out.persistence

import com.goodjob.resume.domain.Prediction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PredictionJpaRepository : JpaRepository<Prediction, Long> {

    @Query("SELECT p FROM Prediction p where p.memberId= :member ORDER BY p.createdDate DESC")
    fun findByMemberId(member: Long): List<Prediction>
}