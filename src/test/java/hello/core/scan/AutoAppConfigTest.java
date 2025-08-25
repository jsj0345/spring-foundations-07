package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
@Component를 달아 놓은 클래스들

-RateDiscountPolicy
-MemoryMemberRepository
-OrderServiceImpl
-MemberServiceImpl

 */

public class AutoAppConfigTest {

  @Test
  void basicScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    /*
    빈 이름이 같은 경우엔 어떨지 테스트를 해보자.

    MemberServiceImpl, OrderServiceImpl의 컴포넌트 이름을 Service라고 지정해보자

    방법은 다음과 같다. @Component("Service")

    방법대로 한 다음에 테스트를 실행하면
    Caused by: org.springframework.context.annotation.ConflictingBeanDefinitionException:
    Annotation-specified bean name 'Service' for bean class [hello.core.order.OrderServiceImpl] conflicts with existing
    이런 오류가 발생한다. 빈의 이름은 중복되면 안된다.
     */


    MemberService memberService = ac.getBean(MemberService.class);
    assertThat(memberService).isInstanceOf(MemberService.class);


    /*
    이 코드를 실행하면 Run창에 있는 결과물중에 ClassPathBeanDefinitionScanner가 있음.
    이게 컴포넌트 스캔이 정상적으로 잘 되고 있다는 것을 보여줌.

    그리고 singleton bean이라고 표기가 되어 있음.
    autoAppConfig, rateDiscountPolicy, memberServiceImpl, memoryMemberRepository
    17:44:38.694 [Test worker] DEBUG o.s.b.f.s.DefaultListableBeanFactory --
                Creating shared instance of singleton bean 'autoAppConfig'
    17:44:38.697 [Test worker] DEBUG o.s.b.f.s.DefaultListableBeanFactory --
                Creating shared instance of singleton bean 'rateDiscountPolicy'
    17:44:38.699 [Test worker] DEBUG o.s.b.f.s.DefaultListableBeanFactory --
                Creating shared instance of singleton bean 'memberServiceImpl'
    17:44:38.715 [Test worker] DEBUG o.s.b.f.s.DefaultListableBeanFactory --
                Creating shared instance of singleton bean 'memoryMemberRepository'
    Autowiring by type from bean name 'memberServiceImpl' via constructor to bean named 'memoryMemberRepository'

    Autowired된 것도 보여줌.

    @Component부터 스캔하고 안에 @Bean이 있으면 스프링 컨테이너에 다 등록.

     */

    /*
    AutoAppConfig에 있는 memoryMemberRepository 빈과 @Component가 있는 memoryMemberRepository는 서로 빈 이름이 겹치는데
    수동으로 등록한 빈이 자동 빈을 오버라이딩 함. (이 얘기가 @Bean이 @Component로 등록된 것보다 우선 순위.)
    Overriding bean definition for bean 'memoryMemberRepository' with a different definition
    replacing [Generic bean: class=hello.core.member.MemoryMemberRepository;

    테스트에서 실행 하지말고 CoreApplication에서 실행 해보자.
    Exception encountered during context initialization - cancelling refresh attempt:
    org.springframework.beans.factory.support.BeanDefinitionOverrideException:

    이런게 뜰거임. ㅣ
     */
  }

}

/*
public class AutoAppConfigTest {

  @Test
  void basicScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
  }
}

public class AutoAppConfigTest {

  @Test
  void basicScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
  }

}
 */