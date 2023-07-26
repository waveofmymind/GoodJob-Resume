package com.goodjob.resume.application.facade

import com.goodjob.resume.application.`in`.DeletePredictionUseCase
import com.goodjob.resume.application.`in`.FindPredictionUseCase
import com.goodjob.resume.application.`in`.RegisterPredictionUseCase
import com.goodjob.resume.application.out.DeletePredictionPort
import com.goodjob.resume.application.out.FindPredictionPort
import com.goodjob.resume.application.out.RegisterPredictionPort
import com.goodjob.resume.common.error.InvalidRequestException
import com.goodjob.resume.domain.Prediction
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PredictionFacade(
    private val registerPredictionPort: RegisterPredictionPort,
    private val findPredictionPort: FindPredictionPort,
    private val deletePredictionPort: DeletePredictionPort
) : RegisterPredictionUseCase, FindPredictionUseCase, DeletePredictionUseCase {

    @Transactional
    override fun registerPrediction(command: RegisterPredictionCommand) {
        val prediction = command.toEntity()
        prediction.addTitle(command.titles)
        prediction.addContent(command.contents)
        if (prediction.titles.size != prediction.contents.size) {
            throw InvalidRequestException()
        }
        registerPredictionPort.registerPrediction(prediction)
    }

    override fun findPrediction(id: Long): FindPredictionResponse {
        val prediction = findPredictionPort.findPredictionById(id)
        return FindPredictionResponse.toResponse(prediction)
    }

    override fun findPredictions(memberId: Long): List<FindPredictionResponse> {
        val predictions = findPredictionPort.findPredictionsByMemberId(memberId)
        return predictions.map { FindPredictionResponse.toResponse(it) }
    }

    @Transactional
    override fun deletePrediction(id: Long) {
        deletePredictionPort.deletePrediction(id)
    }
}
