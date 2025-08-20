package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

  @Test
  void statefulServiceSingleton() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    StatefulService statefulService1 = ac.getBean(StatefulService.class);
    StatefulService statefulService2 = ac.getBean(StatefulService.class);

    /*
    getBean 마지막 정리 빈을 등록 할 때는 메서드 반환형을 등록하는게 아니라 구현부 안에 있는 return을 봐야함.
    즉, new StatefulService()가 반환.
     */

    //ThreadA: A사용자가 10000원을 주문.
    statefulService1.order("userA",10000);
    //ThreadB: B사용자가 20000원을 주문.
    statefulService2.order("userB",20000);

    //ThreadA: 사용자A 주문 금액 조회
    int price = statefulService1.getPrice();
    System.out.println("price = " + price);

    assertThat(statefulService1.getPrice()).isEqualTo(20000);

  }

  static class TestConfig {
    @Bean
    public StatefulService statefulService() {
      return new StatefulService();
    }
  }

}

/*
public class StatefulServiceTest {

   @Test
   void statefulServiceSingleton() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
    StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

    //ThreadA: A사용자 10000원 주문.
    statefulService1.order("userA", 10000);

    //ThreadB: B사용자 20000원 주문.
    statefulService2.order("userB", 20000);

    //ThreadA: 사용자A 주문 금액 조회
    int price = statefulService1.getPrice();

    //ThreadA: 사용자A는 10000원을 기대했지만, 기대와 다르게 20000원 출력.
    System.out.println("price = " + price);

    assertThat(statefulService1.getPrice()).isEqualTo(20000);
  }

  static class TestConfig {

   @Bean
   public StatefulService statefulService() {
     return new StatefulService();
   }

  }

}

public class StatefulServiceTest {

  @Test
  void statefulServiceSingleton() {
    ApplicationContext ac = AnnotationConfigApplicationContext(TestConfig.class):
    StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
    StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

    // ThreadA : A 사용자 10000원 주문
    statefulService1.order("userA", 10000);
    // ThreadB : B 사용자 20000원 주문
    statefulService2.order("userB", 20000);

    //ThreadA : 사용자A 주문 금액 조회
    int price = statefulService1.getPrice();
    //결과는 10000원. 왜냐하면 같은 객체를 공유하고 있어서 값을 바꾸면 당연히 바뀜.
    System.out.println("price = " + price);

    Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
 }

 static class TestConfig {

   @Bean
   public StatefulService statefulService() {
     return new StatefulService();
   }

 }

}





 */