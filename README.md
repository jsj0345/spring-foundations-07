# Spring Foundations 07

Spring Core의 핵심 개념을 학습하고 예제 코드로 정리한 저장소입니다.

DI, IoC 컨테이너, Bean 등록과 조회, 컴포넌트 스캔, 의존관계 주입, 싱글톤 컨테이너 등 Spring Framework의 기본 동작 원리를 학습했습니다.

## 학습 목적

Spring을 단순히 사용하는 것을 넘어, 객체를 직접 생성하고 의존관계를 연결하는 방식에서 Spring 컨테이너가 객체를 관리하는 방식으로 전환되는 흐름을 이해하기 위해 정리했습니다.

Spring MVC와 Spring Boot를 학습하기 전에 필요한 DI, IoC, Bean, Singleton 개념을 예제 코드로 확인하는 데 중점을 두었습니다.

## 학습 내용

- 객체지향 설계와 역할/구현 분리
- 수동 의존관계 주입
- Spring 컨테이너와 Bean 등록
- Bean 조회 방식
- Singleton Container
- Component Scan
- 의존관계 자동 주입
- 다양한 Bean 등록 방식
- Configuration 설정
- 테스트 코드를 활용한 Spring 동작 확인

## 디렉터리 구조

```text
spring-foundations-07
├── gradle
├── src
│   ├── main
│   │── docs 
│   └── test
│       └── java
│           └── hello
│               └── core
├── build.gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## 학습 포인트

- 객체를 직접 생성하고 연결하는 방식과 Spring 컨테이너가 Bean을 관리하는 방식의 차이를 학습했습니다.
- DI와 IoC 개념을 통해 객체 간 결합도를 낮추는 방식을 이해했습니다.
- `@Configuration`, `@Bean`, `@ComponentScan` 등을 사용해 Bean을 등록하는 방식을 학습했습니다.
- Singleton Container를 통해 Spring이 기본적으로 Bean을 하나의 객체로 관리하는 흐름을 확인했습니다.
- 테스트 코드를 활용해 Spring 컨테이너의 동작을 검증했습니다.

## 실행 환경

- Java
- Spring
- Gradle
- IntelliJ IDEA
- JUnit

## 참고
- 코드 출처 : 김영한님 강의 -> 스프링 핵심 원리 - 기본편
- 웹 기본 지식 내용 정리할 때 참고했던 곳 : 모든 개발자를 위한 HTTP 웹 기본 지식 