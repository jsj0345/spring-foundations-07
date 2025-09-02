package hello.core.scope;

import static org.assertj.core.api.Assertions.*;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;


public class PrototypeProviderTest {

  @Test
  void providerTest() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class); // 두개의 클래스 빈으로 등록.

    ClientBean clientBean1 = ac.getBean(ClientBean.class); // 형태에 맞는거 있는지 확인, 객체 꺼내오기
    int count1 = clientBean1.logic();
    assertThat(count1).isEqualTo(1); // 검증

    ClientBean clientBean2 = ac.getBean(ClientBean.class);
    int count2 = clientBean2.logic();
    assertThat(count2).isEqualTo(1);

  }

  static class ClientBean {

    @Autowired
    private ApplicationContext ac; // 이전 코드와는 다르게 ClientBean에서 PrototypeBean을 의존 하지않고 ApplicationContext에서 직접 꺼내옴.

    /*
    의존관계를 외부에서 주입(DI) 받는게 아니라 이렇게 직접 필요한 의존관계를 찾는 것을 Dependency LookUp(DL) 의존관계 조회(탐색)이라 한다.

    그런데 이렇게 스프링의 애플리케이션 컨텍스트 전체를 주입받게 되면 , 스프링 컨테이너에 종속적인 코드가 되고, 단위 테스트도 어려워짐.
     */

    public int logic() {
      PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class); // 객체 생성 후, 초기화 콜백 및 클라이언트에 반환.
      prototypeBean.addCount();
      int count = prototypeBean.getCount();
      return count;
    }
  }

  @Scope("prototype")
  static class PrototypeBean {

    private int count = 0;

    public void addCount() {
      count++;
    }

    public int getCount() {
      return count;
    }

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
