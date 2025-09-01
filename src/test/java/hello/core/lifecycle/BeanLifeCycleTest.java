package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

  @Test
  public void lifeCycleTest() {
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
    System.out.println("a"); // a를 둔 이유는 @PostConstruct가 언제 작동하는지 보려고 하기 위함.
    // NetworkClient client = ac.getBean(NetworkClient.class);
    ac.close();

    /*
    인터페이스들의 관계

    BeanFactory -> ApplicationContext -> ConfigurableApplicationContext ->  AnnotationConfigApplicationContext

    ->으로 갈수록 하위 관계임

    close 메서드는 ConfigurableApplicationContext에 있으므로 메서드를 쓰고싶으면 ConfigurableApplicationContext, AnnotationConfigApplicationContext형으로 써야함.
     */
  }

  @Configuration
  static class LifeCycleConfig {
    @Bean // (initMethod = "init", destroyMethod = "close")
    public NetworkClient networkClient() {
      NetworkClient networkClient = new NetworkClient();
      networkClient.setUrl("http://hello-spring.dev");
      return networkClient;
    }
  }

}

/*
package hello.core.lifecycle;

public class BeanLifeCycleTest {

  @Test
  public void lifeCycleTest() {
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
    NetworkClient client = ac.getBean(LifeCycleConfig.class);
    ac.close(); // 스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
 }

  @Configuration
  static class LifeCycleConfig {

    @Bean
    public NetworkClient networkClient() {
      NetworkClient networkClient = new NetworkClient();
      networkClient.setUrl("http://hello-spring.dev");
      return networkClient;
    }

  }

}

public class BeanLifeCycleTest {

  @Test
  public void lifeCycleTest() {
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
    NetworkClient client = ac.getBean(NetworkClient.class);
    ac.close();
  }

  @Configuration
  static class LifeCycleConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public NetworkClient networkClient() {
      NetworkClient networkClient = new NetworkClient();
      networkClient.setUrl("http:hello-spring.dev");
      return networkClient;
    }

  }

}




 */
