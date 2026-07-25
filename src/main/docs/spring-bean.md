# 스프링 싱글톤 빈 관리 복습

> 스프링 컨테이너가 객체를 하나로 공유하는 이유와 `@Configuration`이 빈 생성 호출을 어떻게 제어하는지 정리했다.

## 1. 싱글톤 컨테이너가 필요한 이유

웹 애플리케이션은 많은 요청이 동시에 들어온다. 요청마다 서비스와 저장소 객체를 새로 만들면 생성 비용과 메모리 사용이 커진다.

스프링 컨테이너는 기본적으로 빈을 싱글톤으로 관리한다.

```java
MemberService first = context.getBean(MemberService.class);
MemberService second = context.getBean(MemberService.class);

assert first == second;
```

애플리케이션 코드가 직접 싱글톤 패턴을 구현하지 않아도 컨테이너가 인스턴스 공유를 담당한다.

## 2. 공유 객체는 무상태로 설계하기

싱글톤 빈 하나를 여러 요청 스레드가 함께 사용한다.

```java
class StatefulService {

    private int price;

    void order(int price) {
        this.price = price;
    }
}
```

사용자 A가 저장한 값을 사용자 B가 덮어쓰면 A가 잘못된 결과를 읽을 수 있다.

공유 빈에서는 다음 원칙을 적용한다.

- 특정 사용자 값을 인스턴스 필드에 저장하지 않기
- 변경 가능한 공유 필드를 최소화하기
- 결과는 지역 변수나 반환값으로 전달하기
- 불변 객체를 선호하기
- 정말 공유 변경이 필요하면 동시성 제어 적용하기

```java
int order(int price) {
    return price;
}
```

지역 변수는 요청을 처리하는 각 스레드의 호출 흐름에 놓이므로 인스턴스 필드보다 안전하다.

## 3. `@Configuration` 안의 `@Bean`

```java
@Configuration
class AppConfig {

    @Bean
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    OrderService orderService() {
        return new OrderServiceImpl(memberRepository());
    }
}
```

코드만 보면 `memberRepository()`가 여러 번 호출되어 객체도 여러 개 만들어질 것처럼 보인다.

하지만 `@Configuration` 클래스는 스프링이 처리한 확장 객체로 등록될 수 있다. `@Bean` 메서드를 호출할 때 이미 컨테이너에 빈이 있으면 기존 객체를 돌려주도록 개입한다.

```text
@Bean 메서드 호출
→ 컨테이너에 이미 등록됐는지 확인
→ 있으면 기존 빈 반환
→ 없으면 생성 후 등록
```

이 때문에 설정 메서드 사이의 직접 호출에서도 싱글톤 관계를 유지할 수 있다.

## 4. CGLIB로 확장된 설정 클래스

컨테이너에서 설정 빈의 실제 타입을 확인하면 원래 클래스 이름 뒤에 스프링이 만든 확장 타입 정보가 보일 수 있다.

이는 설정 클래스의 메서드 호출을 가로채 컨테이너의 빈 관리 규칙을 적용하기 위한 방식이다.

중요한 것은 CGLIB 이름을 외우는 것보다 `@Configuration`이 단순한 설정 표시를 넘어 `@Bean` 간 호출을 컨테이너 조회처럼 동작하게 만들 수 있다는 점이다.

## 5. `@Configuration` 없이 `@Bean`만 사용하면

설정 클래스를 컨테이너에 등록하면서 `@Configuration`을 사용하지 않으면 각 `@Bean` 자체는 등록할 수 있다.

그러나 같은 클래스 내부에서 `memberRepository()`를 직접 호출하는 코드는 일반 Java 메서드 호출처럼 새 객체를 만들 수 있다.

```text
컨테이너가 @Bean을 등록하는 것
≠ 내부 메서드 직접 호출까지 항상 가로채는 것
```

스프링 빈 사이의 의존성을 메서드 파라미터로 전달받으면 직접 호출 의존을 줄일 수 있다.

```java
@Bean
MemberService memberService(MemberRepository repository) {
    return new MemberServiceImpl(repository);
}
```

## 6. 주의할 점

- 빈이 싱글톤이라고 클래스 자체가 자동으로 스레드 안전해지는 것은 아니다.
- 설정 메서드를 애플리케이션 코드에서 일반 팩토리처럼 직접 호출하면 컨테이너 관리 밖의 객체가 생길 수 있다.
- 공유 빈에 요청별 상태를 저장하면 동시 요청에서 데이터가 섞일 수 있다.
- 싱글톤 범위는 보통 하나의 스프링 컨테이너 기준이다.

## 핵심 정리

- 스프링은 기본적으로 빈 인스턴스를 하나 만들어 공유한다.
- 싱글톤 빈은 요청별 변경 상태를 필드에 보관하지 않는 편이 안전하다.
- `@Configuration`은 `@Bean` 메서드 호출에 컨테이너 규칙을 적용할 수 있다.
- 설정 클래스의 내부 호출도 기존 빈을 반환해 싱글톤 관계를 유지한다.
- `@Configuration`이 없으면 직접 메서드 호출은 일반 Java 호출로 동작할 수 있다.
