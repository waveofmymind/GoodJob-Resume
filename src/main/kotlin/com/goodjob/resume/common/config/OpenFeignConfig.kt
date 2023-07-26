package com.goodjob.resume.common.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients("com.goodjob.resume.adapter.in.web")
class OpenFeignConfig {
}