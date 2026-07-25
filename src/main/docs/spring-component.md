# 컴포넌트 스캔과 자동 주입 복습

> 수동으로 빈을 나열하지 않고 클래스를 탐색해 등록하는 과정과 중복 빈 충돌을 정리했다.

## 1. 컴포넌트 스캔

```java
@Configuration
@ComponentScan
class AutoAppConfig {
}
```

`@ComponentScan`은 지정된 패키지 범위에서 컴포넌트 후보를 찾고 스프링 빈으로 등록한다.

```java
@Component
class MemoryMemberRepository
        implements MemberRepository {
}
```

기본 빈 이름은 일반적으로 클래스 이름의 첫 글자를 소문자로 바꾼 형태가 된다.

```text
MemoryMemberRepository
→ memoryMemberRepository
```

직접 이름을 지정할 수도 있다.

```java
@Component("memberRepository")
```

## 2. 자동 의존관계 주입

스캔으로 객체를 등록하는 것만으로는 서비스와 저장소의 연결이 완성되지 않는다.

```java
@Component
class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Autowired
    MemberServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }
}
```

스프링은 생성자 파라미터 타입에 맞는 빈을 찾아 주입한다.

생성자가 하나뿐인 경우에는 `@Autowired`를 생략할 수 있는 환경도 있다.

## 3. 탐색 시작 위치

```java
@ComponentScan(basePackages = "hello.core")
```

지정한 패키지와 하위 패키지를 탐색한다.

클래스 위치를 기준으로 지정할 수도 있다.

```java
@ComponentScan(
    basePackageClasses = AutoAppConfig.class
)
```

문자열 패키지명은 오타나 패키지 이동에 취약할 수 있어 기준 클래스를 사용하는 방식이 더 안전할 때가 있다.

스프링 부트에서는 메인 설정 클래스를 애플리케이션 최상위 패키지에 두면 하위 구조가 자연스럽게 스캔 범위에 포함된다.

## 4. 포함과 제외 필터

특정 애노테이션이 붙은 클래스만 추가하거나 제외하도록 필터를 만들 수 있다.

```java
@ComponentScan(
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = MyIncludeComponent.class
    ),
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = MyExcludeComponent.class
    )
)
```

필터는 가능하지만 애플리케이션 구조가 복잡해질 수 있다. 기본 패키지 구조와 표준 스테레오타입으로 해결할 수 있는지 먼저 보는 편이 좋다.

## 5. 컴포넌트 계열 애노테이션

다음 애노테이션도 컴포넌트 스캔 대상으로 사용된다.

- `@Controller`
- `@Service`
- `@Repository`
- `@Configuration`

단순 등록 기능뿐 아니라 계층의 역할을 표현하고 일부 부가 기능과 연결되기도 한다.

## 6. 자동 등록끼리 충돌

서로 다른 클래스가 같은 빈 이름으로 자동 등록되면 어떤 빈을 선택해야 할지 알 수 없어 오류가 발생할 수 있다.

패키지 구조, 클래스명, 명시적 컴포넌트 이름을 확인해야 한다.

## 7. 자동 등록과 수동 등록 충돌

```java
@Component
class MemoryMemberRepository {
}

@Bean("memoryMemberRepository")
MemberRepository repository() {
    return new MemoryMemberRepository();
}
```

같은 이름을 자동 스캔과 수동 설정이 함께 사용하면 실행 환경과 설정에 따라 덮어쓰기 또는 오류가 발생할 수 있다.

의도하지 않은 오버라이딩은 구성 실수를 숨기므로 중복 이름을 제거하고 하나의 등록 방식을 명확히 선택하는 편이 안전하다.

## 8. 수동 등록과 자동 등록의 선택

자동 스캔은 업무 빈이 많을 때 반복 설정을 줄인다.

수동 등록은 다음 경우에 의도가 더 잘 보일 수 있다.

- 외부 라이브러리 객체 등록
- 기술 설정 빈
- 생성 과정이 복잡한 객체
- 여러 구현 중 선택 정책을 설정하는 경우

둘을 함께 사용할 수 있지만 어느 영역을 어떤 방식으로 관리할지 규칙을 정해야 한다.

## 핵심 정리

- `@ComponentScan`은 패키지에서 컴포넌트 후보를 찾아 빈으로 등록한다.
- 생성자 자동 주입으로 스캔된 빈 사이의 관계를 연결할 수 있다.
- 설정 클래스의 위치는 기본 스캔 범위를 결정하는 중요한 기준이다.
- 필터로 대상을 조정할 수 있지만 과도한 설정은 구조를 어렵게 만든다.
- 같은 이름의 자동·수동 빈이 충돌하지 않도록 등록 정책을 분명히 해야 한다.
