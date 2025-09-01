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

}


스프링 빈의 이벤트 라이프사이클

스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료

초기화 콜백 : 빈이 생성되고 빈의 의존관계 주입이 완료된 후 호출
소멸전 콜백 : 빈이 소멸되기 직전에 호출.

참고 : 싱글톤 빈들은 스프링 컨테이너가 종료될 때 싱글톤 빈들도 함께 종료되기 때문에 스프링 컨테이너가 종료 되기 직전에
소멸전 콜백이 일어난다. 뒤에서 설명하겠지만 싱글톤 처럼 컨테이너의 시작과 종료까지 생존하는 빈도 있지만,
생명주기가 짧은 빈들도 있는데 이 빈들은 컨테이너와 무관하게 해당 빈이 종료되기 직전에
소멸전 콜백이 일어남.

package hello.core.lifecycle;

public class NetworkClient implements InitializingBean, DisposableBean {

  private String url;

  public NetworkClient() {
    System.out.println("생성자 호출, url = " + url);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // 서비스 시작시 호출
  public void connect() {
    System.out.println("connect: " + url);
  }

  public void call disConnect() {
    System.out.println("close : " + url);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    connect();
    call("초기화 연결 메시지");
  }

  @Override
  public void destroy() throws Exception {
    disConnect();
  }

}

위처럼 코드를 짜면 스프링 전용 인터페이스를 이욯한 것.
초기화, 소멸 메서드의 이름 변경 불가능.
내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없음.

@Override 한 메서드를

  public void init() {
    System.out.println("NetworkClient.init");
    connect();
    call("초기화 연결 메시지");
  }

  public void close() {
    System.out.println("NetworkClient.close");
    disConnect();
  }
로 바꿔보자.

메서드 이름을 자유롭게 줄 수 있다.
스프링 빈이 스프링 코드에 의존하지 않는다.
코드가 아니라 설정 정보를 사욯하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 사용할 수 있다.

@PostConstruct, @PreDestroy 애노테이션을 달아보자.

  @PostConstruct
  public void init() {
    System.out.println("NetworkClient.init");
    connect();
    call("초기화 연결 메시지");
  }

  @PreDestroy
  public void close() {
    System.out.println("NetworkClient.close");
    disConnect();
  }



 */
