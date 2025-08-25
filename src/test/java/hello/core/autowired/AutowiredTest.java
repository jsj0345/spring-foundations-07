package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

  @Test
  void AutowiredOption() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
  }

  static class TestBean {

    @Autowired(required = false) // 원래 기본값은 required = true, true여서 연결해야하는 Bean이 존재해야함.
    // 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안된다.
    public void setNoBean1(Member noBean1) {
      System.out.println("noBean1 = " + noBean1);
    }

    @Autowired
    public void setNoBean2(@Nullable Member noBean2) { // 호출은 되는데 null로 옴.
      System.out.println("noBean2 = " + noBean2);
    }

    @Autowired
    public void setNoBean3(Optional<Member> noBean3) { // 스프링 빈이 없으면 Optional.empty로 나옴.
      System.out.println("noBean3 = " + noBean3);
    }

    /*
    @Override
    public String toString() {
    return value != null
        ? String.format("Optional[%s]", value)
        : "Optional.empty";
    }
    */

  }
}

/*
public class AutowiredTest {

  void AutowiredOption() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
  }

  static class TestBean {

    // 호출 안됨.
    @Autowired(required = false) // 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안된다.
    public void setNoBean1(Member member) {
     System.out.println("setNoBean1 = " + member);
    }

    // null 호출
    @Autowired // 자동 주입할 대상이 없으면 null이 입력된다.
    public void setNoBean2(@Nullable Member member) {
      System.out.println("setNoBean2 = " + member);
    }

    // Optional.empty 호출
    @Autowired(required = false)
    public void setNoBean3(Optional<Member> member) {
      System.out.println("setNoBean3 = " + member);
    }

  }

}

 */
