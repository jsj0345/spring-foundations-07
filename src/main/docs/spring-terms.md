# 스프링 핵심 용어 복습

> IoC, DI, 컨테이너, 빈과 설정 메타정보를 서로 연결해서 정리했다.

## 1. 제어의 역전

객체가 자신에게 필요한 구현을 직접 만들면 생성과 실행 흐름을 스스로 결정한다.

```java
class OrderServiceImpl {

    private final DiscountPolicy policy =
            new RateDiscountPolicy();
}
```

이 구조에서는 서비스가 구체 구현 선택까지 책임진다.

외부 설정이 객체를 만들고 관계를 연결하면 서비스는 자신의 업무 로직에 집중한다.

```java
class OrderServiceImpl {

    private final DiscountPolicy policy;

    OrderServiceImpl(DiscountPolicy policy) {
        this.policy = policy;
    }
}
```

객체 생성과 연결의 제어권이 애플리케이션 객체 밖으로 이동하는 관점을 IoC라고 이해했다.

## 2. 프레임워크와 라이브러리

### 라이브러리

내 코드가 필요할 때 라이브러리 기능을 호출한다.

```text
내 코드 → 라이브러리 호출
```

### 프레임워크

프레임워크가 전체 실행 흐름을 관리하고 정해진 시점에 내가 작성한 코드를 호출한다.

```text
프레임워크 → 내 코드 호출
```

JUnit이 테스트 메서드를 찾아 실행하거나 스프링이 빈 생명주기 콜백을 호출하는 흐름이 예가 된다.

차이는 특정 API 모양보다 전체 제어 흐름을 누가 쥐고 있는지에 있다.

## 3. 의존관계 주입

클래스가 인터페이스를 참조하는 것은 코드에서 확인 가능한 정적 의존관계다.

```text
OrderServiceImpl → DiscountPolicy
```

실행할 때 어떤 구현 객체가 연결되는지는 동적 의존관계다.

```text
DiscountPolicy → RateDiscountPolicy
```

DI는 외부에서 실제 객체 참조를 전달해 실행 시점의 관계를 완성한다.

덕분에 클라이언트 코드를 변경하지 않고 구현을 교체하기 쉬워진다.

## 4. IoC 컨테이너와 DI 컨테이너

객체를 생성하고 보관하며 필요한 의존관계를 연결하는 구성 요소를 IoC 컨테이너 또는 DI 컨테이너라고 부른다.

스프링에서는 의존관계 연결 기능을 강조해 DI 컨테이너라는 표현으로 이해하기 쉽다.

## 5. 스프링 컨테이너

`ApplicationContext`는 스프링 컨테이너의 중심 인터페이스다.

```java
ApplicationContext context =
        new AnnotationConfigApplicationContext(
                AppConfig.class
        );
```

컨테이너는 설정 정보를 읽어 다음 작업을 수행한다.

- 빈 생성
- 빈 이름과 인스턴스 관리
- 의존관계 연결
- 빈 조회
- 생명주기 관리
- 이벤트, 메시지, 환경 정보 같은 부가 기능 제공

## 6. 스프링 빈

컨테이너가 생성하고 관리하는 객체가 스프링 빈이다.

```java
@Configuration
class AppConfig {

    @Bean
    MemberService memberService() {
        return new MemberServiceImpl();
    }
}
```

기본 빈 이름은 메서드 이름인 `memberService`가 된다. 실제로 등록되는 객체는 메서드가 반환한 인스턴스다.

빈 이름은 컨테이너 안에서 충돌하지 않게 관리해야 한다.

## 7. BeanFactory와 ApplicationContext

`BeanFactory`는 빈 등록과 조회의 기본 기능을 정의한다.

`ApplicationContext`는 그 기능을 포함하면서 애플리케이션 개발에 필요한 여러 부가 기능을 제공한다.

일반적인 스프링 애플리케이션에서는 `ApplicationContext`를 컨테이너로 사용한다.

## 8. BeanDefinition

스프링은 Java 설정, XML, 컴포넌트 스캔 등 서로 다른 입력 형식으로 빈을 등록할 수 있다.

컨테이너가 매번 각 형식의 세부 문법에 직접 의존하지 않도록 빈 설정을 공통 메타정보로 표현한다.

`BeanDefinition`에는 다음과 같은 설정 정보가 담길 수 있다.

- 빈 클래스 또는 생성 방식
- 빈 이름
- 스코프
- 지연 초기화 여부
- 초기화·소멸 메서드
- 의존관계 관련 정보

```text
Java 설정 ─┐
XML 설정  ─┼→ BeanDefinition → 스프링 컨테이너
스캔 정보 ─┘
```

이 추상화 덕분에 설정 입력 방식이 달라도 컨테이너는 공통된 빈 관리 절차를 사용할 수 있다.

## 9. 용어 연결

```text
IoC
→ 객체 생성과 실행 제어가 외부로 이동

DI
→ 외부가 실제 의존 객체를 연결

스프링 컨테이너
→ 객체를 만들고 DI와 생명주기를 관리

스프링 빈
→ 컨테이너가 관리하는 객체

BeanDefinition
→ 빈을 만들기 위한 공통 설정 정보
```

## 핵심 정리

- IoC는 애플리케이션 객체가 갖던 제어권을 외부가 관리하는 관점이다.
- DI는 실행 시점의 실제 객체 관계를 외부에서 연결한다.
- 프레임워크는 전체 흐름을 관리하며 사용자 코드를 호출한다.
- `ApplicationContext`는 빈 관리 외에도 여러 애플리케이션 기능을 제공한다.
- 스프링 빈은 컨테이너가 생성하고 관리하는 객체다.
- `BeanDefinition`은 다양한 설정 방식을 공통 메타정보로 추상화한다.
