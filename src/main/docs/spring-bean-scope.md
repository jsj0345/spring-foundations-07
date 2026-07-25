# 스프링 빈 스코프 복습

> 빈이 얼마나 오래 존재하고 어느 범위에서 공유되는지를 싱글톤, 프로토타입, 웹 스코프로 나누어 정리했다.

## 1. 스코프의 의미

빈 스코프는 스프링 빈의 생성 시점과 공유 범위를 결정한다.

대표적인 범위:

- `singleton`: 컨테이너마다 하나의 인스턴스를 공유
- `prototype`: 조회할 때마다 새 인스턴스 생성
- `request`: HTTP 요청 하나에서 공유
- `session`: HTTP 세션 동안 공유
- `application`: 서블릿 컨텍스트 범위에서 공유

기본값은 싱글톤이다.

## 2. 싱글톤과 프로토타입 비교

### 싱글톤

```text
getBean()
→ 같은 인스턴스

getBean()
→ 앞과 같은 인스턴스
```

컨테이너가 생성부터 종료까지 생명주기를 관리한다.

### 프로토타입

```text
getBean()
→ 새 인스턴스 A

getBean()
→ 새 인스턴스 B
```

스프링은 생성, 의존관계 주입, 초기화까지만 담당하고 완성된 객체를 호출자에게 넘긴다.

그 이후 사용과 종료 정리는 호출자가 책임져야 한다. 프로토타입 빈의 `@PreDestroy`가 컨테이너 종료 시 자동 호출될 것으로 기대하면 안 된다.

## 3. 싱글톤에 프로토타입을 직접 주입할 때의 함정

```java
@Component
class ClientBean {

    private final PrototypeBean prototypeBean;

    ClientBean(PrototypeBean prototypeBean) {
        this.prototypeBean = prototypeBean;
    }
}
```

`ClientBean`은 싱글톤이므로 생성 시점에 프로토타입 빈 하나를 주입받고 계속 보관한다.

```text
싱글톤 생성 시 프로토타입 A 주입
→ 여러 메서드 호출에서 계속 A 사용
```

프로토타입이라는 선언만 보고 메서드를 호출할 때마다 새 객체가 나올 것으로 예상하면 실제 동작과 다르다.

## 4. 지연 조회로 새 인스턴스 얻기

```java
@Component
class ClientBean {

    private final ObjectProvider<PrototypeBean> provider;

    ClientBean(ObjectProvider<PrototypeBean> provider) {
        this.provider = provider;
    }

    void logic() {
        PrototypeBean bean = provider.getObject();
        bean.use();
    }
}
```

필요한 시점에 컨테이너에 다시 조회하므로 호출할 때마다 새 프로토타입 빈을 받을 수 있다.

`ObjectProvider`는 스프링 기능에 의존한다. 표준적인 공급자 추상화가 필요하다면 `jakarta.inject.Provider`를 검토할 수 있다.

```java
private final Provider<PrototypeBean> provider;
```

둘 다 객체 생성 시점을 늦추는 역할을 하지만 사용할 라이브러리와 필요한 부가 기능이 다르다.

## 5. 웹 스코프

### request

하나의 HTTP 요청이 시작해서 응답할 때까지 같은 빈을 사용한다. 다른 요청에는 별도 인스턴스가 생성된다.

요청 ID나 요청별 로그 정보처럼 요청끼리 섞이면 안 되는 데이터를 담을 수 있다.

### session

사용자 HTTP 세션과 같은 생명주기를 가진다.

### application

서블릿 컨텍스트와 같은 범위에서 공유된다.

웹 스코프는 웹 환경에서만 동작하며 해당 범위가 끝날 때까지 스프링이 빈을 관리한다.

## 6. 요청 스코프를 싱글톤에 주입할 때

애플리케이션 시작 시점에는 아직 HTTP 요청이 없다. 이때 싱글톤 컨트롤러가 request 빈의 실제 객체를 바로 요구하면 생성할 요청 범위를 찾지 못할 수 있다.

### ObjectProvider 사용

```java
private final ObjectProvider<MyLogger> loggerProvider;

public void logic() {
    MyLogger logger = loggerProvider.getObject();
    logger.log("request message");
}
```

실제 요청이 들어온 뒤 조회한다.

### 스코프 프록시 사용

```java
@Scope(
    value = WebApplicationContext.SCOPE_REQUEST,
    proxyMode = ScopedProxyMode.TARGET_CLASS
)
@Component
class MyLogger {
}
```

싱글톤에는 프록시 객체를 미리 주입하고, 메서드를 호출하는 시점에 현재 요청의 실제 빈을 찾아 위임한다.

호출 코드가 단순해지지만 실제 객체 생성 시점이 겉으로 잘 보이지 않는다는 점은 이해해야 한다.

## 7. 스코프 선택의 한계

프로토타입이나 요청 스코프를 남용하면 객체 흐름을 추적하기 어려워질 수 있다.

먼저 상태를 지역 변수나 명시적인 파라미터로 전달할 수 있는지 검토하고, 생명주기와 공유 범위가 분명할 때 스코프를 선택하는 편이 좋다.

## 핵심 정리

- 스코프는 빈의 생명주기와 공유 범위를 결정한다.
- 프로토타입은 조회할 때마다 생성되지만 종료 관리는 호출자 책임이다.
- 싱글톤에 직접 주입한 프로토타입은 하나의 객체로 고정된다.
- `ObjectProvider`나 `Provider`로 필요한 시점에 다시 조회할 수 있다.
- request 스코프는 HTTP 요청마다 인스턴스를 분리한다.
- 요청 빈을 싱글톤에 연결할 때는 지연 조회나 스코프 프록시가 필요할 수 있다.
