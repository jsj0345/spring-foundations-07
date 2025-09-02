package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonTest {

  @Test
  void singletonBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
    // SingletonBean 클래스에서 @Bean을 찾는다. 있으면 빈으로 등록.
    // 없으니까 클래스만 빈으로 등록된다. 이제 빈 등록은 끝났고 의존관계 주입 할 것도 없으니까 초기화 콜백 실행 (@PostConstruct)

    SingletonBean singletonBean1 = ac.getBean(SingletonBean.class); // 싱글톤 빈이라 참조값 같을 것으로 예상.
    SingletonBean singletonBean2 = ac.getBean(SingletonBean.class); // 싱글톤 빈이라 참조값 같을 것으로 예상.
    System.out.println("singletonBean1 = " + singletonBean1);
    System.out.println("singletonBean2 = " + singletonBean2);

    assertThat(singletonBean1).isSameAs(singletonBean2); // 같은지 테스트

    ac.close(); // 끝나기전에 소멸전 콜백 실행. (@PreDestory)
  }

  @Scope("singleton")
  static class SingletonBean {

    @PostConstruct // 초기화 콜백
    public void init() {
      System.out.println("SingletonBean.init");
    }

    @PreDestroy // 소멸전 콜백
    public void destroy() {
      System.out.println("SingletonBean.destroy");
    }
    
  }
}

/*
public class SingletonTest {

  @Test
  public void singletonBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
    SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
    SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
    System.out.println("singletonBean1 = " + singletonBean1);
    System.out.println("singletonBean2 = " + singletonBean2);
    assertThat(singletonBean1).isSameAs(singletonBean2);

    ac.close();
  }

  @Scope("singleton")
  static class SingletonBean {

    @PostConstruct
    public void init() {
      System.out.println("SingletonBean.init");
    }

    @PreDestroy
    public void destroy() {
      System.out.println("SingletonBean.destroy");
    }
  }

}

package hello.core.scope;

public class SingletonTest {

  @Test
  public void singletonBeanFind() {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

    SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
    SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);

    System.out.println("singletonBean1 = " + singletonBean1);
    System.out.println("singletonBean2 = " + singletonBean2);
    assertThat(singletonBean1).isSameAs(singletonBean2);

    ac.close();
 }

 @Scope("singleton")
 static class SingletonBean {

   @PostConstruct
   public void init() {
     System.out.println("SingletonBean.init");
   }

   @PreDestroy
   public void destroy() {
     System.out.println("SingletonBean.destroy");
   }

 }

}





 */
