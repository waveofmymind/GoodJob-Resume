## 해당 저장소는 GoodJob 프로젝트에서 생성 AI 서비스를 분리한 저장소입니다.

기존 프로젝트는 [여기](https://github.com/waveofmymind/GoodJob)를 참고해주세요.

## 기술 스택

- Kotlin 1.9.0
- Spring Boot 3.1.2
- Spring Data JPA
- Spring Kafka
- Jasypt
- Spring Cloud Config
- Spring Cloud OpenFeign
- Kotest, Mockk

## API 명세

### /predictions/{predictionId}

{predictionId}에 해당하는 예측 결과를 조회합니다.

### /members/{memberId}/predictions

{memberId}에 해당하는 예측 결과를 조회합니다.(복수)

## 비즈니스 로직 플로우 차트

![스크린샷 2023-07-31 오후 7 24 50](https://github.com/waveofmymind/GoodJob-Resume/assets/93868431/35292be2-f5f6-4a8a-87d8-05da9c2decb6)

## 테스트 결과

![스크린샷 2023-07-27 오후 9 47 14](https://github.com/waveofmymind/GoodJob-Resume/assets/93868431/c6ab055c-6929-4277-8661-27980dc83cc2)
