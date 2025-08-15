package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  //@Bean으로 등록되어 있는 것들의 반환 객체를 Bean으로 등록. 물론 메서드의 이름을 Bean으로 지정함.

  @Test
  @DisplayName("모든 빈 출력하기")
  void findAllBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) { // iter을 탭하면 향상된 for문이 나옴
      Object bean = ac.getBean(beanDefinitionName); // Object로 꺼내짐 타입을 몰라서 모든 타입을 담을 수 있는 Object로.
      System.out.println("name = " + beanDefinitionName + " object = " + bean);

      // beanDefinitionName을 key, bean을 value로 봄.

      /*
      실제 결과를 보면 @Bean으로 등록하지 않은 빈들도 나옴.
      따라서, 직접 등록한 Bean들만 보는 코드를 짜자.
       */
    }
  }

  @Test
  @DisplayName("애플리케이션 빈 출력하기")
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) { // iter을 탭하면 향상된 for문이 나옴
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
      // getBeanDefinition은 Bean에 대한 정보

      // Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
      // Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈

      if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) { // 직접 등록한 애플리케이션의 빈을 보여주기 위한 조건
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionName + ", Object = " + bean);
      }
    }
  }
}

/*
class ApplicationContextInfoTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("모든 빈 출력하기")
  void findAllBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName); // 여기서 Object로 받은 이유는 어떤 형일지 모르기 때문.
      System.out.println("name = " + beanDefinitionName + ", object = " + bean);
    }
  }

  @Test
  @DisplayName("애플리케이션 빈 출력하기")
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName); // getBeanDefinition은 빈에 관한 정보를 반환.

      //Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
      //Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
      getRole메서드는 빈의 역할을 반환하는 메서드임 그런데..
      ROLE_APPLICATION -> 0 (개발자가 직접 등록한 애플리케이션 빈은 0을 반환.)
      ROLE_SUPPORT -> 1 (애플리케이션 빈을 지원하기 위한 구성 빈은 1을 반환.)
      ROLE_INFRASTRUCTURE -> 2 (스프링 내부 동작을 위해 등록한 인프라 빈은 2를 반환.)

      if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionName + ", object = " + bean);
      }
   }
  }
}

class ApplicationContextInfoTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("모든 빈 출력하기")
  void findAllBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();

    for(String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionName + ", object = " + bean);
      // beanDefinitionName을 키로 bean을 value로.

      실제 결과를 보면 수동으로 등록한 빈 이외에도 다른 빈들이 나옴.
    }
  }

  @Test
  @DisplayName("애플리케이션 빈 출력하기")
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

      //Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
      //Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
      if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionName + ", object = " + bean);
      }
    }
  }
}
___________________________________________________________________________________

class ApplicationContextInfoTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("모든 빈 출력하기")
  void findAllBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionName + ", bean = " + bean);
    }
  }

  @Test
  @DisplayName("애플리케이션 빈 출력하기")
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();

    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

      //Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
      //Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈

      if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionName + " , bean = " + bean);
      }
    }
  }
}










 */
