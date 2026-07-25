# 스프링 의존관계 주입 복습

> 주입 방식의 차이와 생성자 주입을 기본으로 선택하는 이유, 같은 타입의 빈이 여러 개일 때 해결 방법을 정리했다.

## 1. 생성자 주입

```java
@Component
class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    OrderServiceImpl(
            MemberRepository memberRepository,
            DiscountPolicy discountPolicy
    ) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

객체가 만들어질 때 필수 의존관계를 함께 전달받는다.

내가 생성자 주입을 기본값으로 보는 이유는 다음과 같다.

- 객체 생성 이후 의존관계를 바꾸지 않게 설계하기 쉬움
- `final` 필드 사용 가능
- 필요한 협력 객체가 없으면 인스턴스 생성 자체가 실패
- 순수 Java 테스트에서 직접 객체를 만들기 쉬움
- 생성자 파라미터만 봐도 필요한 의존관계가 드러남

생성자가 하나라면 스프링 환경에서 `@Autowired`를 생략할 수 있다.

## 2. 수정자 주입

```java
@Autowired
public void setDiscountPolicy(
        DiscountPolicy discountPolicy
) {
    this.discountPolicy = discountPolicy;
}
```

객체가 생성된 뒤 setter를 통해 주입한다.

의존관계를 선택적으로 넣거나 실행 중 변경해야 하는 특별한 요구에는 사용할 수 있다. 하지만 반드시 필요한 의존성도 setter로 받으면 완성되지 않은 객체가 잠시 존재할 수 있고 필드를 `final`로 만들 수 없다.

## 3. 필드 주입

```java
@Autowired
private MemberRepository memberRepository;
```

코드는 짧지만 클래스 외부에서 의존관계를 전달하기 어렵다.

스프링 컨테이너 없이 단위 테스트하기 불편하고 클래스가 무엇을 필요로 하는지 생성자 계약에 나타나지 않는다.

테스트 코드나 아주 제한된 설정 외에는 생성자 주입이 더 명시적이다.

## 4. 일반 메서드 주입

```java
@Autowired
public void init(
        MemberRepository repository,
        DiscountPolicy policy
) {
    this.repository = repository;
    this.policy = policy;
}
```

한 메서드에서 여러 의존관계를 주입할 수 있다. 특별한 초기화 의미가 없다면 생성자나 수정자보다 의도가 약해질 수 있어 자주 쓰지는 않는다.

## 5. 선택적 의존관계

주입 대상이 없어도 객체가 생성돼야 하는 경우가 있다.

```java
@Autowired(required = false)
void setOptionalService(OptionalService service) {
}
```

```java
@Autowired
void setOptionalService(
        Optional<OptionalService> service
) {
}
```

```java
@Autowired
void setOptionalService(
        @Nullable OptionalService service
) {
}
```

세 방식은 호출 여부와 빈 값 표현이 다르므로 메서드가 반드시 실행돼야 하는지까지 고려해 선택한다.

선택적 의존성이 많다면 클래스 역할이 지나치게 넓은 것은 아닌지도 검토할 필요가 있다.

## 6. Lombok과 생성자 코드

```java
@Component
@RequiredArgsConstructor
class OrderServiceImpl {

    private final MemberRepository repository;
    private final DiscountPolicy policy;
}
```

`@RequiredArgsConstructor`는 `final` 필드 등에 필요한 생성자를 만들어 준다.

반복 코드는 줄지만 실제 생성자 계약을 이해하지 않고 애노테이션에만 의존하면 안 된다. 필드가 추가될 때 생성자 의존성도 함께 늘어난다는 점을 확인해야 한다.

## 7. 같은 타입의 빈이 여러 개일 때

```java
@Component
class FixDiscountPolicy implements DiscountPolicy {
}

@Component
class RateDiscountPolicy implements DiscountPolicy {
}
```

`DiscountPolicy` 타입 빈이 둘이면 타입만으로 하나를 선택할 수 없다.

### 파라미터 이름 또는 필드 이름

```java
OrderServiceImpl(
        DiscountPolicy rateDiscountPolicy
) {
}
```

타입 조회 뒤 이름을 보조 기준으로 사용할 수 있다. 이름 변경에 의존하므로 핵심 정책에는 더 명시적인 방법이 나을 수 있다.

### `@Qualifier`

```java
@Component
@Qualifier("mainDiscountPolicy")
class RateDiscountPolicy implements DiscountPolicy {
}
```

```java
OrderServiceImpl(
    @Qualifier("mainDiscountPolicy")
    DiscountPolicy discountPolicy
) {
}
```

특정 빈을 명시적으로 선택한다.

문자열 오타를 줄이기 위해 `@Qualifier`를 포함한 사용자 정의 애노테이션을 만들 수도 있다.

### `@Primary`

```java
@Component
@Primary
class RateDiscountPolicy implements DiscountPolicy {
}
```

여러 후보 중 기본 선택권을 준다. 특별한 구현만 선택해야 하는 지점에서는 `@Qualifier`가 더 구체적인 지시가 된다.

## 8. 빈을 모두 주입받기

같은 인터페이스의 구현을 전략 목록으로 사용할 수 있다.

```java
@Component
class DiscountService {

    private final Map<String, DiscountPolicy> policies;

    DiscountService(
            Map<String, DiscountPolicy> policies
    ) {
        this.policies = policies;
    }

    int discount(
            String policyName,
            Member member,
            int price
    ) {
        DiscountPolicy policy = policies.get(policyName);
        return policy.discount(member, price);
    }
}
```

Map의 키는 빈 이름이고 값은 해당 타입의 빈이 된다.

새 전략 빈을 추가하면 주입 목록에 자연스럽게 포함되므로 조건문을 늘리지 않고 확장할 수 있다. 다만 외부 입력을 빈 이름에 바로 연결하면 허용하지 않은 전략 선택이나 `null`이 생길 수 있으므로 입력 검증과 명시적인 매핑이 필요하다.

## 9. 생성자 주입도 모든 문제를 해결하지는 않음

생성자 파라미터가 지나치게 많다면 클래스가 너무 많은 책임을 갖는 신호일 수 있다.

순환 참조가 발생했다면 지연 주입으로 숨기기보다 두 클래스의 역할과 의존 방향을 다시 살펴보는 편이 좋다.

## 핵심 정리

- 필수 의존관계는 생성자 주입으로 명확히 드러낼 수 있다.
- 수정자 주입은 선택이나 변경이 필요한 관계에 고려한다.
- 필드 주입은 테스트와 명시성 측면에서 제약이 있다.
- 같은 타입의 빈이 여러 개라면 `@Qualifier`, `@Primary` 등으로 선택한다.
- `List`와 `Map` 주입을 이용하면 여러 구현을 전략처럼 사용할 수 있다.
- 의존성이 너무 많다면 주입 방식보다 클래스 책임을 먼저 점검한다.
