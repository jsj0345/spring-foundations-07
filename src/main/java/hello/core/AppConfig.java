package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 역할에 따른 구현이 잘 보임.
@Configuration // 애플리케이션의 구성 정보를 담당함.
public class AppConfig { // 애플리케이션 전체를 설정하고 구성한다는 의미로 지음.

  /*
  @Bean들을 보면 public 접근 제어자임.
  private으로 저번에 했다가 스프링 실행 안 된적 있음. 주의!

  AppConfig 코드를 보면 재밌는 점이 하나 있다. (싱글톤을 배운 이후)
  원래 스프링은 하나의 객체를 갖는 싱글톤 패턴이다.
  그런데.. 두개의 빈을 살펴보자..

  @Bean memberService() -> memberRepository()를 호출.
  @Bean orderService() -> memberRepository()를 호출.

  총 두번 호출한다. 이러면 객체를 두번 생성한다. 싱글톤 패턴을 사용 하지 않는것 같다.

  한번 테스트를 해보자.

  테스트를 한 결과 그렇지 않다. 한번만 호출한다.

   */

  /*
  한번 더 테스트를 해보기 위해 이번엔 각 메서드마다 call 클래스명.메서드이름을 추가했다.

  먼저, memberService.getMemberRepository 메서드를 보면..

   */

  @Bean
  public MemberService memberService() {
    // 생성자 주입 방식
    System.out.println("call AppConfig.memberService"); // soutm
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    System.out.println("call AppConfig.memberRepository");
    return new MemoryMemberRepository(); // 중복 방지 (역할이 매우 명확함)
  }

  @Bean
  public OrderService orderService() {
    System.out.println("call AppConfig.orderService");
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    //return new FixDiscountPolicy(); // 중복 방지 (역할이 매우 명확함)
    System.out.println("call AppConfig.discountPolicy");
    return new RateDiscountPolicy();
  }

  /*
  FixDiscountPolicy() -> RateDiscountPolicy() 객체로 변경
  할인 정책을 변경해도 AppConfig만 바꾸면 된다.
  클라이언트 코드인 OrderServiceImpl를 포함해서 "사용 영역"의 어떤 코드도 변경할 필요가 없다!!
   */

  /*
  왜 반환형을 MemberService, OrderService로 했을까?

  인터페이스형으로 둬야 오버라이딩한 메서드를 쓸 수 있고 반환값을 담기 쉬우니까(심지어 생성자에 구체적인 클래스 넣기도 쉬움)

  public MemberService memberService() { // 생성자 주입 방식
    return new MemberServiceImpl(new MemoryMemberRepository());
  }

  public OrderService orderService() {
    return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
  }

  여기서 보면 MemoryMemberRepository()가 중복 된다. 중복을 제거해보자. (ctrl + alt + M)
   */



}

/*

코드 다시 작성 하면서 리마인드 해보기.

package hello.core;

// 역할에 따른 구현이 잘 보임.
public class AppConfig {

  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  -> new MemoryMemberRepository()가 중복 됨. 이걸 좀 없애고싶음.

  private MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  private DiscountPolicy discountPolicy() {
    return new FixDiscountPolicy();
  }



좋은 객체 지향 설계의 5가지 원칙의 적용

이번에 회원,주문 엔터티 및 서비스를 구현 할 땐 3가지 원칙을 적용했다.

1. DIP(Dependency Inversion Principle)
2. OCP(Open-Closed Principle)
3. SRP(Single Responsibility Principle)

__________________________________________________________________

package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

  public MemberService memberService() {
    return new MemberServiceImpl(new MemberMemoryRepository());
  }

  public OrderService orderService() {
    return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
  }

  이렇게 코드를 짜면 중복이 있음. 역할에 따른 구현이 보이도록 하자.

}

public class AppConfig {

  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  public DiscountPolicy discountPolicy() {
    return new FixDiscountPolicy();
  }

}

@Configuration
public class AppConfig {

  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }

}

@Configuration // 애플리케이션의 구성 정보를 담당함
public class AppConfig {

  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }

___________________________________________________________________________________

@Configuration
public class AppConfig {

  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), DiscountPolicy());
  }

  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }

}

AppConfig에 설정을 구성한다는 뜻의 @Configuration을 붙여준다.

각 메서드에 @Bean을 붙여준다. 이렇게 하면 스프링 컨테이너에 스프링 빈으로 등록한다.

애플리케이션의 전체 동작 방식을 구성(config) 하기 위해, 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스를 만들자.

package hello.core;

@Configuration
public class AppConfig {

  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }

  클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 할수있음. (DI)
}





 */
