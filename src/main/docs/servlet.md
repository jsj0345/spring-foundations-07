# 서블릿과 서블릿 컨테이너 복습

> HTTP 요청을 직접 파싱하는 부담을 서블릿과 컨테이너가 어떻게 나누어 처리하는지 정리했다.

## 1. 브라우저의 폼 요청

HTML Form에서 사용자가 값을 입력하면 브라우저가 HTTP 메시지를 만들어 서버에 전송한다.

```html
<form action="/members" method="post">
    <input name="username">
    <input name="age">
    <button type="submit">전송</button>
</form>
```

기본 폼 전송의 본문은 다음과 같은 형태가 될 수 있다.

```text
username=userA&age=20
```

서버 개발자가 TCP 연결, 헤더 구분, 본문 파싱, 응답 형식 생성을 매번 직접 구현한다면 업무 로직보다 통신 처리 코드가 훨씬 많아진다.

## 2. 서블릿이 제공하는 추상화

```java
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String username = request.getParameter("username");

        response.setContentType("text/plain");
        response.getWriter().write("hello " + username);
    }
}
```

개발자는 `HttpServletRequest`에서 요청 데이터를 읽고 `HttpServletResponse`에 응답 내용을 작성한다.

컨테이너는 그 앞뒤의 네트워크와 HTTP 처리를 담당한다.

```text
HTTP 요청 수신
→ 요청 객체 생성
→ URL에 맞는 서블릿 호출
→ 응답 객체의 데이터 수집
→ HTTP 응답 메시지 생성
→ 클라이언트에 전송
```

## 3. 요청 객체

`HttpServletRequest`에는 다음과 같은 요청 정보가 정리되어 있다.

- HTTP 메서드
- 요청 URI
- 쿼리 파라미터
- 폼 데이터
- 헤더
- 쿠키
- 본문 입력 스트림

서블릿 코드는 원시 HTTP 문자열 전체를 직접 나누지 않고 필요한 정보를 메서드로 조회한다.

## 4. 응답 객체

`HttpServletResponse`를 이용해 다음을 설정할 수 있다.

- 상태 코드
- 응답 헤더
- 콘텐츠 타입과 문자 인코딩
- 쿠키
- 리다이렉션
- 본문 출력

```java
response.setStatus(HttpServletResponse.SC_OK);
response.setContentType("application/json");
response.getWriter().write("{\"result\":\"ok\"}");
```

상태 코드와 본문 형식을 일치시키는 것이 중요하다.

## 5. 서블릿 컨테이너

톰캣처럼 서블릿 규칙을 구현한 WAS의 구성 요소가 서블릿 컨테이너다.

컨테이너가 담당하는 일:

- 서블릿 객체 생성
- 초기화와 종료
- URL 매핑
- 요청별 메서드 호출
- 요청·응답 객체 제공
- 스레드 배정
- 예외와 보안 관련 처리

개발자는 비즈니스 로직에 집중하고 반복적인 서버 기반 기능을 컨테이너에 맡긴다.

## 6. 싱글톤 관리와 동시 요청

서블릿 객체는 보통 하나만 생성되어 여러 요청에서 함께 사용된다.

```text
요청 A 스레드 ─┐
               ├→ 같은 Servlet 인스턴스
요청 B 스레드 ─┘
```

따라서 요청마다 달라지는 값을 인스턴스 필드에 저장하면 서로 덮어쓸 수 있다.

```java
// 잘못된 예
private String username;
```

다음처럼 요청 처리 메서드의 지역 변수로 두는 편이 안전하다.

```java
String username = request.getParameter("username");
```

공유가 필요한 객체라면 불변으로 만들거나 동시성 제어가 필요하다.

## 7. 한계와 다음 단계

서블릿만으로도 웹 애플리케이션을 만들 수 있지만 요청 파라미터 변환, 검증, 화면 이동, 공통 예외 처리 같은 코드가 반복될 수 있다.

Spring MVC는 서블릿 컨테이너 위에서 동작하며 이런 반복을 컨트롤러, 매핑, 메시지 변환기 같은 더 높은 수준의 기능으로 추상화한다.

## 핵심 정리

- 브라우저는 Form 데이터를 HTTP 요청으로 변환한다.
- 서블릿은 요청과 응답을 객체 형태로 다루게 해 준다.
- 컨테이너는 네트워크 처리, 객체 생명주기, URL 매핑과 스레드 배정을 담당한다.
- 서블릿 인스턴스는 여러 요청이 공유할 수 있으므로 상태를 필드에 두지 않는 편이 안전하다.
- Spring MVC도 결국 서블릿 기반 환경 위에서 실행된다.
