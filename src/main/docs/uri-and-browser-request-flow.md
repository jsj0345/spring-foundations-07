# URI와 브라우저 요청 흐름 복습

> 브라우저에 URL을 입력한 뒤 서버 응답을 받기까지 URI, DNS, TCP/IP, HTTP가 어떻게 이어지는지 정리했다.

## 1. URI와 URL

URI는 인터넷의 리소스를 식별하는 표현이다.

URL은 그중에서도 리소스의 위치와 접근 방법을 나타내는 형태로 자주 사용된다.

```text
https://www.example.com:443/search?q=spring#result
```

구성 요소를 나누면 다음과 같다.

```text
scheme   → https
host     → www.example.com
port     → 443
path     → /search
query    → q=spring
fragment → result
```

## 2. 각 구성 요소의 역할

### scheme

어떤 통신 방식을 사용할지 나타낸다.

### host

접속할 서버의 도메인 이름 또는 IP 주소다.

### port

서버 컴퓨터 안의 대상 애플리케이션을 구분한다. 프로토콜의 기본 포트를 사용하면 URL에서 생략할 수 있다.

### path

서버 안에서 요청할 리소스의 계층적 위치를 나타낸다.

### query

조회 조건 같은 추가 데이터를 `key=value` 형태로 전달한다.

### fragment

문서 내부 위치를 가리킬 수 있으며 일반적인 HTTP 요청에서는 서버로 전달되지 않고 브라우저에서 사용된다.

## 3. 브라우저가 URL을 해석하는 과정

브라우저는 입력된 URL에서 통신 방식, 도메인, 포트, 경로와 쿼리를 분리한다.

도메인만으로는 네트워크 패킷을 보낼 수 없으므로 DNS를 통해 서버 IP를 찾는다.

```text
www.example.com
→ DNS 조회
→ 203.0.113.10
```

## 4. 서버와 연결

브라우저는 얻은 IP와 포트를 사용해 서버와 연결을 준비한다.

TCP를 사용하는 통신이라면 연결 수립 뒤 HTTP 메시지를 보낸다.

```http
GET /search?q=spring HTTP/1.1
Host: www.example.com

```

HTTP 메시지는 전송 계층과 IP 계층의 정보로 감싸져 여러 네트워크 장비를 거쳐 서버로 이동한다.

## 5. 서버의 처리와 응답

서버는 포트에서 요청을 받아 HTTP 메시지를 해석하고 해당 경로의 애플리케이션 로직을 실행한다.

```http
HTTP/1.1 200 OK
Content-Type: text/html

<html>...</html>
```

응답도 네트워크를 거쳐 브라우저에 도착한다. 브라우저는 상태 코드와 헤더를 확인하고 본문을 렌더링하거나 파일로 처리한다.

HTML 안에 CSS, JavaScript, 이미지 주소가 있으면 해당 리소스에 대한 추가 요청이 이어진다.

## 6. 전체 흐름

```text
URL 입력
→ URL 구성 요소 분석
→ DNS로 IP 조회
→ 서버 IP와 포트에 연결
→ HTTP 요청 작성
→ TCP/IP 패킷으로 전달
→ 서버 애플리케이션 처리
→ HTTP 응답 수신
→ 브라우저가 본문 해석
→ 필요한 추가 리소스 요청
```

## 7. 구분해서 기억할 것

```text
URI  → 리소스 식별 표현
DNS  → 도메인을 IP로 조회
IP   → 목적지 컴퓨터 식별
PORT → 컴퓨터 안의 프로그램 식별
HTTP → 요청과 응답의 메시지 규칙
```

## 핵심 정리

- URL은 scheme, host, port, path, query, fragment로 나눌 수 있다.
- 브라우저는 DNS를 통해 도메인에 대응하는 IP를 찾는다.
- IP와 포트로 서버에 연결한 뒤 HTTP 요청을 전송한다.
- 서버 응답을 받은 브라우저는 본문을 해석하고 추가 리소스를 다시 요청한다.
- URI, DNS, IP, PORT, HTTP는 서로 다른 단계의 문제를 해결한다.
