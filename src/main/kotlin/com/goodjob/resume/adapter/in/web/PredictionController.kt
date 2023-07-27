package com.goodjob.resume.adapter.`in`.web

import com.goodjob.resume.application.`in`.FindPredictionUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/predictions")
class PredictionController(
    private val findPredictionUseCase: FindPredictionUseCase
) {

    @GetMapping("/{predictionId}")
    fun findPrediction(@PathVariable("predictionId") predictionId: Long) = findPredictionUseCase.findPrediction(predictionId)

    @GetMapping("/{memberId}")
    fun findPredictions(@PathVariable("memberId") memberId: Long) = findPredictionUseCase.findPredictions(memberId)
}
