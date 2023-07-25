package com.goodjob.resume.common.error

sealed class BusinessException(
    open val errorCode: ErrorCode
) : RuntimeException(errorCode.message)

data class JsonBindingException(
    override val errorCode: ErrorCode = ErrorCode.JSON_BINDING_ERROR
) : BusinessException(errorCode)

data class PredictionNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.PREDICTION_NOT_FOUND
) : BusinessException(errorCode)


