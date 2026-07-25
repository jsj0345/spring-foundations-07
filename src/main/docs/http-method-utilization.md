# HTTP 메서드 활용 복습

> 클라이언트가 서버로 데이터를 전달하는 위치와 요청 상황에 따라 메서드와 본문 형식이 어떻게 달라지는지 정리했다.

## 1. 데이터 전달 위치

클라이언트가 서버에 값을 보낼 때 가장 먼저 구분할 것은 데이터가 URI에 들어가는지 본문에 들어가는지다.

### 쿼리 파라미터

```http
GET /members?team=red&sort=name
```

조회 조건, 검색어, 정렬 기준처럼 리소스를 찾는 조건에 적합하다.

### 메시지 본문

```http
POST /members
Content-Type: application/json

{
  "name": "userA",
  "age": 20
}
```

등록이나 처리에 필요한 구조화된 데이터를 전달할 때 주로 사용한다.

## 2. 정적 데이터 조회

이미지, CSS, 문서처럼 경로만으로 대상을 식별할 수 있다면 단순한 GET 요청으로 충분하다.

```http
GET /images/logo.png
```

추가 조건이 없다면 쿼리 파라미터도 필요하지 않다.

## 3. 동적 데이터 조회

검색이나 목록 필터링은 같은 리소스에 조건만 달라지는 경우가 많다.

```http
GET /products?keyword=keyboard&sort=price
```

조회 요청이므로 GET을 사용하고 조건을 쿼리 문자열에 표현하는 방식이 자연스럽다.

URL에 노출되기 때문에 비밀번호나 민감한 값을 쿼리에 넣지 않는 편이 좋다. 브라우저 기록, 로그, 중간 시스템에 남을 수 있기 때문이다.

## 4. HTML Form 전송

```html
<form action="/members" method="post">
    <input name="name">
    <input name="age">
    <button type="submit">등록</button>
</form>
```

기본 폼 전송에서는 다음과 같은 형식이 자주 사용된다.

```http
Content-Type: application/x-www-form-urlencoded
```

본문은 대략 다음처럼 전달된다.

```text
name=userA&age=20
```

파일 업로드가 포함되면 `multipart/form-data`가 필요하다.

```html
<form action="/files" method="post"
      enctype="multipart/form-data">
```

HTML Form은 메서드 선택에 제약이 있어 수정과 삭제도 POST 기반 경로로 처리하는 경우가 있다.

## 5. HTTP API 전송

브라우저 JavaScript, 모바일 앱, 서버 간 통신에서는 JSON이 자주 사용된다.

```http
POST /orders
Content-Type: application/json

{
  "productId": 7,
  "quantity": 2
}
```

요청 본문을 보낼 때는 서버가 해석할 수 있도록 `Content-Type`을 정확히 지정해야 한다.

## 6. 상황별 정리

| 상황 | 대표 방식 |
|---|---|
| 정적 파일 조회 | GET + 리소스 경로 |
| 검색·정렬·필터 | GET + 쿼리 파라미터 |
| HTML Form 등록 | POST + 폼 인코딩 |
| 파일 업로드 | POST + multipart |
| API 등록·처리 | POST + JSON |
| 전체 교체 | PUT + 본문 |
| 일부 변경 | PATCH + 본문 |

표는 일반적인 선택 기준이며 실제 API 의미에 따라 달라질 수 있다.

## 7. 내가 확인할 설계 질문

- 조회 조건인가, 리소스 상태를 변경하는 데이터인가
- URL에 노출되어도 되는 값인가
- 서버가 기대하는 `Content-Type`은 무엇인가
- 요청을 다시 보내도 안전한가
- 브라우저 Form 제약이 있는가
- 파일처럼 여러 종류의 데이터를 한 요청에 섞어야 하는가

## 핵심 정리

- 검색 조건은 쿼리 파라미터, 등록 데이터는 본문에 두는 경우가 많다.
- 정적 리소스는 경로만으로 조회할 수 있다.
- HTML Form과 HTTP API는 전송 형식과 지원 메서드가 다르다.
- JSON 요청에는 올바른 `Content-Type`이 필요하다.
- 메서드는 데이터 위치보다 요청 의미를 먼저 기준으로 선택해야 한다.
