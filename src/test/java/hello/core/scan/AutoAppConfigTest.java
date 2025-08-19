package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

  @Test
  void basicScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

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

     */
  }

}
