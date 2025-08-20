package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

  @Test
  void configurationTest() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    /*
    최종 정리!

    getBean은 클래스형을 내놓는거지 빈을 직접적으로 호출하진 않음.

    빈을 실질적으로 호출하는건 ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class); 여기임.

    근데 호출을 할때 AppConfig.class에 위에서 아래순으로 보이는 빈들을 호출함. 물론 의존관계때문에 call 호출 순서가 바뀔수도있음.

    결론 이미 빈을 저 위에 코드로 등록했기 때문에 CGLIB 코드에서 무조건 if문에서 else 가지도 않고 if 문 안에서 끝나는거임.
     */

    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    // return new MemberServiceImpl(memberRepository()); -> new MemberServiceImpl(new MemoryMemberRepository)
    OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
    // return new OrderServiceImpl(memberRepository(), discountPolicy()) -> new OrderServiceImpl(new MemoryMemberRepository, ..)
    MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
    // return new MemoryMemberRepository;

    /*
    예상 결과
    call AppConfig.memberService
    call AppConfig.memberRepository
    call AppConfig.orderService
    call AppConfig.memberRepository
    call AppConfig.memberService

    이렇게 call이 5번이 나올거라는게 예상 결과.

    실제 결과는 call이 세번 나온다.

    왜 세번일까? -> 스프링이 싱글톤을 보장해준다는 것은 알겠는데 이유는 다음시간에..!

     */

    MemberRepository memberRepository1 = memberService.getMemberRepository();
    MemberRepository memberRepository2 = orderService.getMemberRepository();

    System.out.println("memberService -> memberRepository1 = " + memberRepository1);
    System.out.println("orderService -> memberRepository2 = " + memberRepository2);
    System.out.println("memberRepository -> memberRepository = " + memberRepository);

    assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
    assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

    /*
    놀랍게도 결과가 똑같음.

    AppConfig를 보면 memberService에서 memberRepository를 호출.

    마찬가지로 orderService에서도 memberRepository를 호출.

    new를 두번 씀. 객체가 두번 생성 되어야함. 근데 테스트 코드를 보면 다 참조값이 같음.
     */

  }

  @Test
  void configurationDeep() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    AppConfig bean = ac.getBean(AppConfig.class);

    System.out.println("bean = " + bean.getClass());
    // bean = class hello.core.AppConfig$$SpringCGLIB$$0
    // 만약에 순수한 클래스라면 class hello.core.AppConfig 가 나와야함
    // 예상과는 다르게 CGLIB가나옴

    /*
    이것은 내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서
    AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 다른 클래스를 스프링 빈으로 등록한 것이다!
     */

    /*
    @Configuration을 빼면 CGLIB이라는 바이트코드 조작 라이브러리를 사용하지 않음.

    순수하게 class hello.core.AppConfig 로 나옴.

    스프링 빈으로는 등록됨. 근데 싱글톤 패턴이 깨짐. (순수한 자바코드가 실행 되는 것.)
     */
  }

}

/*

public class ConfigurationSingletonTest {

  @Test
  void configurationTest() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
    MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

    //모두 같은 인스턴스를 참고함.
    System.out.println("memberService -> memberRepository = " + memberService.getMemberRepository());
    System.out.println("orderService -> memberRepository = " + orderService.getMemberRepository());
    System.out.println("memberRepository = " + memberRepository);

    assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
    assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
  }

  @Test
  void configurationDeep() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    //AppConfig도 스프링 빈으로 등록 된다.
    AppConfig bean = ac.getBean(AppConfig.class);

    System.out.println("bean = " + bean.getClass());
  }

}

최종 정리!

getBean은 클래스형을 내놓는거지 빈을 직접적으로 호출하진 않음.

빈을 실질적으로 호출하는건 ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

근데 호출을 할때 AppConfig.class에 위에서 아래순으로 보이는 빈들을 호출함. 물론 의존관계때문에 call 호출 순서가 바뀔수도 있음.

결론 이미 빈을 저 위에 코드로 등록했기 때문에 CGLIB 코드에서 무조건 if문에서 else 가지도 않고 if 문 안에서 끝나는 것.


public class ConfigurationSingletonTest {

  @Test
  void configurationTest() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

    // 모두 같은 인스턴스를 참고하고 있다.
    System.out.println("memberService -> memberRepository = " + memberService.getMemberRepository());
    System.out.println("orderService -> memberRepository = " + orderService.getMemberRepository());
    System.out.println("memberRepository = " + memberRepository);

    Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
    Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
  }


  @Test
  void configurationDeep() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // AppConfig도 스프링 빈으로 등록된다.
    AppConfig bean = ac.getBean(AppConfig.class);

    System.out.println("bean = " + bean.getClass());

    // 출력: bean = class hello.core.AppConfig$$SpringCGLIB$$0
  }

  왜 CGLIB이 나올까? 원래 정상적으로 나온다면 class hello.core.AppConfig일 것이다.

  그런데 예상과는 다르게 CGLIB이 붙어있다. 이것은 내가 만든 클래스가 아니라

  스프링이 CGLIB이라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은

  임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다!

  이러한 임의의 다른 클래스가 싱글톤이 보장되도록 해준다.

  아마도 다음과 같이 바이트 코드를 조작해서 작성되어 있을 것이다.


  @Bean
  public MemberRepository memberRepository() {

    if (memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면?) {
      return 스프링 컨테이너에서 찾아서 반환;
    } else {
      기존 로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
      return 반환
    }

  }

  @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서
  스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.

  덕분에 싱글톤이 보장됨.

}






 */
