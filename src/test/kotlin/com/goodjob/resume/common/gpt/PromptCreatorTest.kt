package com.goodjob.resume.common.gpt

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class PromptCreatorTest: BehaviorSpec({

    Given("AI 서비스를 사용할 때") {
        val job = "Backend Developer"
        val career = "NewComer"
        val resumeType = "Tech Stack"
        val content = "Kotlin, Spring Boot, JPA, MySQL, AWS"
        When("요청 정보가 모두 올바르면") {
            val result = PromptCreator.generateQuestionMessage(job, career, resumeType, content)
            Then("요청 정보를 기반으로 Prompt를 생성한다.") {
                result.size shouldBe 2
                result[0].role shouldBe "system"
                result[1].role shouldBe "user"
            }
        }
    }
})
