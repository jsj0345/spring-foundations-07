# HTTP API URI 설계 복습

> URI에는 리소스를 표현하고, 실제 동작은 HTTP 메서드에 맡긴다는 기준으로 정리했다.

## 1. 리소스와 행위를 나누기

회원 API를 만들 때 URI를 다음처럼 동사 중심으로 작성할 수도 있다.

```text
/create-member
/find-member
/delete-member
```

하지만 URI가 표현해야 할 대상은 `회원`이라는 리소스다.

```text
/members
/members/{id}
```

조회·등록·수정·삭제라는 행동은 `GET`, `POST`, `PUT`, `PATCH`, `DELETE`가 설명한다.

```text
GET    /members/10
DELETE /members/10
```

같은 URI라도 메서드가 다르면 요청 의도가 달라진다.

## 2. 컬렉션 방식

서버가 새 리소스의 식별자를 결정하는 등록 방식이다.

```http
POST /members
Content-Type: application/json

{
  "name": "userA"
}
```

서버가 회원 번호를 만든 뒤 생성된 위치를 응답할 수 있다.

```http
HTTP/1.1 201 Created
Location: /members/101
```

클라이언트는 등록 시점에 최종 URI를 몰라도 된다.

```text
POST 기반 등록
→ 서버가 식별자와 URI 결정
→ /members는 컬렉션 역할
```

## 3. 스토어 방식

클라이언트가 저장할 리소스의 URI를 미리 알고 직접 지정하는 방식이다.

```http
PUT /files/logo.png
```

파일명처럼 클라이언트가 식별자를 관리하는 상황에 잘 맞는다.

```text
PUT 기반 등록
→ 클라이언트가 URI 결정
→ /files는 스토어 역할
```

`PUT`은 대상 리소스를 전체 교체하는 의미가 강하므로 일부 필드만 변경하려는 요청과는 구분해야 한다.

## 4. 회원과 파일 예시

### 회원 컬렉션

```text
회원 목록   GET    /members
회원 등록   POST   /members
회원 조회   GET    /members/{id}
회원 수정   PATCH  /members/{id}
회원 삭제   DELETE /members/{id}
```

### 파일 스토어

```text
파일 목록   GET    /files
파일 조회   GET    /files/{name}
파일 저장   PUT    /files/{name}
파일 삭제   DELETE /files/{name}
```

API가 컬렉션인지 스토어인지는 등록 식별자를 누가 정하는지로 판단하면 이해하기 쉽다.

## 5. HTML Form의 제약

순수 HTML Form은 일반적으로 `GET`과 `POST`만 사용한다.

그래서 수정과 삭제를 URI의 보조 경로로 표현하는 경우가 있다.

```text
GET  /members/new
POST /members/new
GET  /members/10/edit
POST /members/10/edit
POST /members/10/delete
```

`new`, `edit`, `delete`처럼 행위를 나타내는 경로를 컨트롤 URI라고 볼 수 있다.

HTTP 메서드만으로 자연스럽게 표현하기 어려울 때 사용할 수 있지만, API 전체를 동사 URI로 만들기보다 제한된 상황에만 두는 편이 구조를 읽기 쉽다.

## 6. 설계할 때 확인할 것

- URI가 리소스 이름을 중심으로 되어 있는가
- 등록할 식별자를 서버와 클라이언트 중 누가 결정하는가
- 전체 교체와 부분 수정이 구분되어 있는가
- HTML Form의 메서드 제약 때문에 컨트롤 URI가 필요한가
- 생성 성공 시 새 리소스 위치를 알려 줄 필요가 있는가

## 핵심 정리

- URI는 리소스를 식별하고 메서드는 행동을 나타낸다.
- 서버가 URI를 만들면 POST 기반 컬렉션이 자연스럽다.
- 클라이언트가 URI를 정하면 PUT 기반 스토어를 생각할 수 있다.
- HTML Form은 메서드 제약 때문에 컨트롤 URI가 필요할 수 있다.
- 동사형 URI는 기본 규칙이 아니라 보완 수단으로 사용한다.
