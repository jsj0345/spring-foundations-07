package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

  @Test
  void prototypeBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

    System.out.println("find prototypeBean1");
    PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class); // 빈을 조회할때 생성, 초기화 메서드 실행.

    System.out.println("find prototypeBean2");
    PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

    System.out.println("prototypeBean1 = " + prototypeBean1); // 참조값이 다름. 매번 새로 생성하기 때문.
    System.out.println("prototypeBean2 = " + prototypeBean2);

    assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

    ac.close();
  }

  @Scope("prototype")
  static class PrototypeBean {

    @PostConstruct // 초기화 콜백
    public void init() {
      System.out.println("PrototypeBean.init");
    }

    @PreDestroy // 소멸전 콜백
    public void destroy() {
      System.out.println("PrototypeBean.destroy");
    }

  }
}

/*
public class PrototypeTest {

  @Test
  public void prototypeBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
    System.out.println("find prototypeBean1");
    PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
    System.out.println("find prototypeBean2");
    PrototypeBean prototypeBean2 - ac.getBean(PrototypeBean.class);
    System.out.println("prototypeBean1 = " + prototypeBean1);
    System.out.println("prototypeBean2 = " + prototypeBean2);
    assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
    ac.close();
  }

  @Scope("prototype")
  static class PrototypeBean {

    @PostConstruct
    public void init() {
      System.out.println("PrototypeBean.init");
    }

    @PreDestroy
    public void destroy() {
      System.out.println("PrototypeBean.destroy");
    }

  }

}

package hello.core.scope;

public class PrototypeTest {

  @Test
  public void prototypeBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
    System.out.println("find prototypeBean1");
    PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
    System.out.println("find prototypeBean2");
    PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
    System.out.println("prototypeBean1 = " + prototypeBean1);
    System.out.println("prototypeBean2 = " + prototypeBean2);
    assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
    ac.close();
  }

  @Scope("prototype")
  static class PrototypeBean {

    @PostConstruct
    public void init() {
      System.out.println("PrototypeBean.init");
    }

    @PreDestroy
    public void destroy() {
      System.out.println("PrototypeBean.destroy");
    }

  }

}

싱글톤 빈은 스프링 컨테이서 생성 시점에 초기화 메서드가 실행 되지만,
프로토타입 스코프의 빈은 스프링 컨테이너에서 빈을 조회할 때 생성되고, 초기화 메서드도 실행된다.

프로토타입 빈을 2번 조회했으므로 완전히 다른 스프링 빈이 생성되고, 초기화도 2번 실행 됐다.

싱글톤 빈은 스프링 컨테이너가 관리하기 때문에 스프링 컨테이너가 종료될 때 빈의 종료 메서드가 실행되지만,
프르토타입 빈은 스프링 컨테이너가 생성과 의존관계 주입 그리고 초기화까지만 관여하고 더는 관여 X.
 */
