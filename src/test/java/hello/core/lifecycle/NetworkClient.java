package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient /* implements InitializingBean, DisposableBean */ {

  private String url;

  public NetworkClient() {
    System.out.println("생성자 호출, url = " + url);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  //서비스 시작시 호출
  public void connect() {
    System.out.println("connect: " + url);
  }

  public void call(String message) {
    System.out.println("call: " + url + ", message = " + message);
  }

  //서비스 종료시 호출
  public void disconnect() {
    System.out.println("close: " + url);
  }

  @PostConstruct
  public void init() {
    System.out.println("NetworkClient.init");
    connect();
    call("초기화 연결 메시지");
  }

  @PreDestroy
  public void close() {
    System.out.println("NetworkClient.close");
    disconnect();
  }



  /*
  @Override
  public void afterPropertiesSet() throws Exception { // 의존관계 주입이 끝나면 메서드 실행 (AnnotationConfigApplicationContext 이후 실행)
    System.out.println("NetworkClient.afterPropertiesSet");
    connect();
    call("초기화 연결 메시지");
  }

  @Override
  public void destroy() throws Exception { // 빈이 종료될 때 호출. (BeanLifeCycleTest 에서 ac.close() 직전에 실행)
    System.out.println("NetworkClient.destroy");
    disconnect();
  }

   */


  /*
  @PostConstruct
  public void init()  { // 의존관계 주입이 끝나면 메서드 실행 (AnnotationConfigApplicationContext 이후 실행)
    System.out.println("NetworkClient.init");
    connect();
    call("초기화 연결 메시지");
  }

   */

  /*
  @PreDestroy
  public void close() { // 빈이 종료될 때 호출. (BeanLifeCycleTest 에서 ac.close() 직전에 실행)
    System.out.println("NetworkClient.close");
    disconnect();
  }

   */

}

/*
package hello.core.lifecycle;

public class NetworkClient {

  private String url;

  public NetworkClient() {
    System.out.println("생성자 호출, url = " + url);
    connect();
    call("초기화 연결 메시지");
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // 서비스 시작시 호출
  public void connect() {
    System.out.println("connect: " + url);
  }

  public void call(String message) {
    System.out.println("call: " + url + " message: " + message);
  }

  // 서비스 종료시 호출
  public void disconnect() {
    System.out.println("close: " + url);
  }

  public void afterPropertiesSet() throws Exception { // 의존관계 주입이 끝난 후에 실행.
    System.out.println("NetworkClient.afterPropertiesSet");
    connect();
    call("초기화 연결 메시지");
  }

  public void destroy() throws Exception { // 빈이 끝나기 전에 호출.
    System.out.println("NetworkClient.destroy");
    disconnect();
  }

}


 */
