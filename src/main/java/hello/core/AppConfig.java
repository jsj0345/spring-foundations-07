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

  @Bean
  public MemberService memberService() { // 생성자 주입 방식
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository(); // 중복 방지 (역할이 매우 명확함)
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    //return new FixDiscountPolicy(); // 중복 방지 (역할이 매우 명확함)
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


 */
