# 스프링 빈 생명주기 콜백 복습

> 객체 생성과 초기화는 같은 시점이 아니며, 스프링이 의존관계 주입 전후를 어떻게 구분하는지 중심으로 정리했다.

## 1. 생성자에서 연결 작업을 시작하면 생기는 문제

네트워크 클라이언트처럼 설정값을 주입받은 뒤 연결해야 하는 객체를 생각해 보자.

```java
public class NetworkClient {

    private String url;

    public NetworkClient() {
        connect();
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
```

생성자가 실행되는 순간에는 `setUrl()`이 아직 호출되지 않았을 수 있다. 이때 연결을 시작하면 `url`이 없는 상태로 초기화 로직이 동작한다.

내가 이해한 빈의 준비 과정은 다음과 같다.

```text
객체 생성
→ 의존관계 주입
→ 초기화 콜백
→ 실제 사용
→ 종료 전 콜백
→ 컨테이너 종료
```

생성자는 필수 값을 받아 객체의 기본 조건을 만드는 데 적합하고, 외부 연결이나 리소스 준비는 주입이 끝난 뒤 수행하는 편이 안전하다.

## 2. 초기화와 종료 콜백이 필요한 경우

- 데이터베이스 커넥션 풀 준비
- 네트워크 소켓 연결
- 백그라운드 작업 시작
- 파일이나 외부 자원 정리
- 애플리케이션 종료 전 연결 해제

초기화 로직과 종료 로직을 짝으로 생각하면 자원 누수를 줄이기 쉽다.

## 3. 전용 인터페이스 방식

```java
public class NetworkClient
        implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() {
        connect();
    }

    @Override
    public void destroy() {
        disconnect();
    }
}
```

스프링이 정한 시점에 메서드가 호출된다는 장점이 있다. 다만 애플리케이션 클래스가 스프링 전용 인터페이스에 직접 의존하고 메서드 이름도 고정된다.

외부 라이브러리 클래스에는 인터페이스를 새로 구현시킬 수 없으므로 적용하기 어렵다.

## 4. 설정 정보에서 메서드 지정

```java
@Bean(initMethod = "init", destroyMethod = "close")
NetworkClient networkClient() {
    NetworkClient client = new NetworkClient();
    client.setUrl("https://example.test");
    return client;
}
```

객체 내부에 스프링 인터페이스를 넣지 않고도 초기화와 종료 메서드를 연결할 수 있다.

이 방식은 직접 수정할 수 없는 외부 클래스의 공개 메서드를 생명주기 콜백으로 등록할 때 특히 유용하다.

문자열로 메서드 이름을 지정하므로 이름을 잘못 적었는지 실행 전에 놓칠 수 있다는 점은 확인해야 한다.

## 5. 애노테이션 방식

```java
@PostConstruct
public void init() {
    connect();
}

@PreDestroy
public void close() {
    disconnect();
}
```

클래스 안에서 초기화와 정리 의도가 바로 보이고 컴포넌트 스캔 방식과 잘 맞는다.

단, 소스 코드를 수정할 수 없는 라이브러리 객체에는 애노테이션을 붙일 수 없다. 그 경우에는 `@Bean`의 `initMethod`, `destroyMethod`가 더 현실적이다.

## 6. 선택 기준

| 상황 | 선택 |
|---|---|
| 직접 작성한 일반적인 빈 | `@PostConstruct`, `@PreDestroy` |
| 외부 라이브러리 객체 | `@Bean(initMethod, destroyMethod)` |
| 오래된 코드나 인터페이스 기반 규칙 | `InitializingBean`, `DisposableBean` |

초기화 메서드에서 객체 생성 자체를 다시 하거나 무거운 업무 로직을 모두 처리하면 컨테이너 시작이 길어질 수 있다. 생명주기 콜백에는 빈이 사용될 준비와 자원 정리에 필요한 작업만 두는 편이 좋다.

## 핵심 정리

- 생성 직후에는 의존관계 주입이 끝나지 않았을 수 있다.
- 외부 연결은 주입이 끝난 초기화 시점에 시작한다.
- 종료 콜백에서는 연결, 스레드, 파일 같은 자원을 정리한다.
- 직접 만든 빈은 애노테이션 방식이 단순하다.
- 수정할 수 없는 객체는 `@Bean` 설정 방식으로 연결할 수 있다.
