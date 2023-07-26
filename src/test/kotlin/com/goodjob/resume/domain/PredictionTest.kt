package com.goodjob.resume.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PredictionTest : StringSpec({
    "Prediction이 생성된다." {
        // given
        val memberId = 1L
        val serviceType = ServiceType.EXPECTED_QUESTION
        val titleList = listOf("title1", "title2")
        val contentList = listOf("content1", "content2")

        // when
        val prediction = Prediction(memberId, serviceType)
        prediction.addTitle(titleList)
        prediction.addContent(contentList)


        // then
        prediction.getMember shouldBe memberId
        prediction.titles[0].title shouldBe titleList[0]
        prediction.contents[0].content shouldBe contentList[0]
        prediction.serviceType shouldBe serviceType
    }
})