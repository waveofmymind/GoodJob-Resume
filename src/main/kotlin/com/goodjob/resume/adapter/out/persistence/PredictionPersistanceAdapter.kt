package com.goodjob.resume.adapter.out.persistence

import com.goodjob.resume.application.out.DeletePredictionPort
import com.goodjob.resume.application.out.FindPredictionPort
import com.goodjob.resume.application.out.RegisterPredictionPort
import com.goodjob.resume.common.error.PredictionNotFoundException
import com.goodjob.resume.domain.Prediction
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PredictionPersistenceAdaptor(
    private val predictionJpaRepository: PredictionJpaRepository
) : RegisterPredictionPort, FindPredictionPort, DeletePredictionPort {

    override fun findPredictionById(id: Long?): Prediction {
        return predictionJpaRepository.findByIdOrNull(id) ?: throw PredictionNotFoundException()
    }


    override fun findPredictionsByMemberId(memberId: Long): List<Prediction> {
        return predictionJpaRepository.findByMemberId(memberId)
    }

    override fun registerPrediction(prediction: Prediction) {
        predictionJpaRepository.save(prediction)
    }

    override fun deletePrediction(id: Long) {
        predictionJpaRepository.findByIdOrNull(id)?:PredictionNotFoundException()
        predictionJpaRepository.deleteById(id)
    }
}
