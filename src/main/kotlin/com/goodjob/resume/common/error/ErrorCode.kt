package com.goodjob.resume.common.error

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: HttpStatus, val message: String) {

    // USER
    DUPLICATE_REGISTER(HttpStatus.CONFLICT, "이미 가입된 유저입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다."),
    UNIDENTIFIED_USER(HttpStatus.UNAUTHORIZED, "사용자 정보가 일치하지 않습니다."),
    UNIDENTIFIED_PASSWORD(HttpStatus.UNAUTHORIZED, "기존 비밀번호가 일치하지 않습니다."),

    // PREDICTION
    JSON_BINDING_ERROR(HttpStatus.BAD_REQUEST, "JSON 파싱에 실패하였습니다."),

    // QUESTION
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 문제입니다."),

    // TOKEN
    TOKEN_VERIFY_FAIL(HttpStatus.UNAUTHORIZED, "토큰 검증에 실패하였습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    EMAIL_SEND_FAIL(HttpStatus.BAD_REQUEST, "이메일 전송에 실패하였습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    PREDICTION_NOT_FOUND(HttpStatus.NOT_FOUND, "예상 답변을 찾을 수 없습니다.")
}
