package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AllBeanTest {

  @Test
  void findAllBean() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
    /*
    한가지 오해를 풀자. AnnotationConfigApplicationContext 생성자에 넘긴 클래스들은 그 자체가 빈 후보로 등록된다.
    애노테이션이 아무것도 없어도 "그 클래스 자신"을 일반 빈으로 등록한다.

    그럼 최종적으로는 AnnotationConfigApplicationContext안에 매개변수로 클래스가 들어갔다고 가정.
    클래스는 자동적으로 스프링 컨테이너에서 관리하는 "Bean"인 거고 이제 그안에 있는 메서드들도 다 Bean으로 등록.
    (가능은 한데 CGLIB에 의한 싱글톤 패턴 보장은 @Configuration 안에서만 가능. @Component 안에 있는 @Bean을 쓰는건 권장X)
    (그래서 @Bean은 되도록이면 @Configuration 안에서만 쓰는게 좋음)
    근데 @Autowired같은 경우에는 생성자가 1개면 생략 하니까 알아서 형태에 맞는 것들을 주입해주는데 그 형태에 맞는 것들은 당연히 빈으로 등록되어야 한다.

    @Configuration은 클래스에서만 사용 가능.
    */
    DiscountService discountService = ac.getBean(DiscountService.class);
    Member member = new Member(1L, "userA", Grade.VIP); // 멤버 엔티티 생성
    int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

    assertThat(discountService).isInstanceOf(DiscountService.class); // Junit 테스트
    assertThat(discountPrice).isEqualTo(1000); // Junit 테스트

    int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
    assertThat(rateDiscountPrice).isEqualTo(2000);

  }


  static class DiscountService { // AnnotationConfigApplicationContext에 의해서 빈으로 등록된다.
    private final Map<String, DiscountPolicy> policyMap;
    private final List<DiscountPolicy> policies;

    // 생성자가 1개면 @Autowired 생략 된다.
    public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) { // 둥록된 빈들중에 형태에 맞는거 주입.
      this.policyMap = policyMap;
      this.policies = policies;
      System.out.println("policyMap = " + policyMap);
      System.out.println("policies = " + policies);
    }

    /*
    출력 결과
    policyMap = {fixDiscountPolicy=hello.core.discount.FixDiscountPolicy@3249a1ce, rateDiscountPolicy=hello.core.discount.RateDiscountPolicy@4dd94a58}
    policies = [hello.core.discount.FixDiscountPolicy@3249a1ce, hello.core.discount.RateDiscountPolicy@4dd94a58]

    여기서 출력 결과는 소문자가 나온 이유는 컴포넌트 스캔을 할때 맨 앞 문자를 소문자로 바꿈.
    */

    public int discount(Member member, int price, String discountCode) {
      DiscountPolicy discountPolicy = policyMap.get(discountCode); // 키값에 맞는 객체를 꺼내옴.

      System.out.println("discountCode = " + discountCode);
      System.out.println("discountPolicy = " + discountPolicy);

      return discountPolicy.discount(member, price);

    }
  }

}

/*
public class AllBeanTest {

  @Test
  void findAllBean() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    DiscountService discountService = ac.getBean(DiscountService.class);
    Member member = new Member(1L, "userA", Grade.VIP);
    int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

    assertThat(discountService).isInstanceOf(DiscountService.class);
    assertThat(discountPrice).isEqualTo(1000);
  }

  static class DiscountService {

    private final Map<String, DiscountPolicy> policyMap;
    private final List<DiscountPolicy> policies;

    public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
      this.policyMap = policyMap;
      this.policies = policies;
      System.out.println("policyMap = " + policyMap);
      System.out.println("policies = " + policies);
    }

    public int discount(Member member, int price, String discountCode) {

      DiscountPolicy discountPolicy = policyMap.get(discountCode);

      System.out.println("discountCode = " + discountCode);
      System.out.println("discountPolicy = " + discountPolicy);

      return discountPolicy.discount(member, price);
   }

  }

}

public class AllBeanTest {

  @Test
  void findAllBean() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
    DiscountService discountService =  ac.getBean(DiscountService.class);
    Member member = new Member(1L, "userA", Grade.VIP);
    int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

    assertThat(discountService).isInstanceOf(DiscountService.class);
    assertThat(discountPrice).isEqualTo(1000);
  }

  static class DiscountService {

    private final Map<String, DiscountPolicy> policyMap;
    private final List<DiscountPolicy> policies;

    // 생성자가 1개면 자동으로 @Autowired 추가.
    public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
      this.policyMap = policyMap;
      this.policies = policies;
      System.out.println("policyMap = " + policyMap);
      System.out.println("policies = " + policies);
    }

    public int discount(Member member, int price, String discountCode) {

      DiscountPolicy discountPolicy = policyMap.get(discountCode);

      System.out.println("discountCode = " + discountCode);
      System.out.println("discountPolicy = " + discountPolicy);

      return discountPolicy.discount(member, price);
   }

  }

}


 */
