package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
//@Scope(value = "request")
@Scope(value = "request" , proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

  private String uuid;
  private String requestURL;

  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }

  public void log(String message) {
    System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message); // uuid로 HTTP 요청 구분.
    // requestURL 정보도 추가로 넣어서 어떤 URL을 요청해서 남은 로그인지 확인하자.
  }

  @PostConstruct
  public void init() {
    uuid = UUID.randomUUID().toString();
    System.out.println("[" + uuid + "] request scope bean create:" + this);
  }

  @PreDestroy
  public void close() {
    System.out.println("[" + uuid + "] request scope bean close:" + this);
  }

}

/*
package hello.core.common;

@Component
@Scope(value = "request")
public class MyLogger {

  private String uuid;
  private String requestURL;

  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }

  public void log(String message) {
    System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
  }

  @PostConstruct
  public void init() {
    uuid = UUID.randomUUID().toString();
    System.out.println("[" + uuid + "] request scope bean create:" + this);
  }

  @PreDestroy
  public void close() {
    System.out.println("[" + uuid + "] request scope bean close:" + this);
  }

}

package hello.core.common;

@Component
@Scope(value = "request")
public class MyLogger {

  private String uuid;
  private String requestURL;

  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }

  public void log(String message) {
    System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
  }

  @PostConstruct
  public void init() {
    uuid = UUID.randomUUID().toString();
    System.out.println("[" + uuid + "] request scope bean create: " + this);
  }

  @PreDestroy
  public void close() {
    System.out.println("[" + uuid + "] request scope bean close: " + this);
  }

}

@Component
@Scope(value = "request")
public class MyLogger {

  private String uuid;
  private String requestURL;

  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }

  public void log(String message) {
    System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
  }

  @PostConstruct
  public void init() {
    uuid = UUID.randomUUID().toString();
    System.out.println("[" + uuid + "] request scope bean create:" + this);
  }

  @PreDestroy
  public void close() {
    System.out.println("[" + uuid + "] request scope bean close:" + this);
  }

}
 */
