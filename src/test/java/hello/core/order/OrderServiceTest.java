package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class OrderServiceTest {

  //MemberService memberService = new MemberServiceImpl();
  //OrderService orderService = new OrderServiceImpl();

  MemberService memberService;
  OrderService orderService;

  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig();
    memberService = appConfig.memberService();
    orderService = appConfig.orderService();
  }

  // 제대로 동작 하는지 테스트. 할인 금액이랑 일치 하는지를 봄.
  @Test
  void createOrder() {
    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 10000);
    Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

  }

  // 테스트를 여러개 돌리고싶으면 각 패키지의 상위 패키지에다가 우클릭해서 Run.
}

/*
class OrderServiceTest {

  MemberService memberService; // DIP를 지키기 위해 인터페이스만 의존.
  OrderService orderService; // DIP를 지키기 위해 인터페이스만 의존.

  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig(); // AppConfig 클래스에서 어떤걸 주입할지 봐야함.
    memberService = appConfig.memberService();

   * memberService 메서드는 MemberService형을 반환함
     근데 반환을 할 때, 하위 형태로 반환함. MemberServiceImpl을 반환. 근데 여기에는 MemoryMemberRepository를 주입.
     생성자에 MemberRepository형을 넣어야하니까..! *

    orderService = appConfig.orderService(); // 위랑 똑같은 논리.
  }

  // 제대로 동작 하는지 테스트. 할인 금액이랑 일치 하는지를 봄.
  @Test
  void createOrder() {
    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member); // AppConfig에 있는 memberService 메서드를 활용하여 return new MemberServiceImpl(~); 을 받았기 때문.

    Order order = orderService.createOrder(memberId, "itemA", 10000);
    Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
  }

  // 테스트를 여러개 돌리고싶으면 각 패키지의 상위 패키지에다가 우클릭해서 Run.


@BeforeEach
public void beforeEach() {
  AppConfig appConfig = new AppConfig();
  memberService = appConfig.memberService();
  orderService = appConfig.orderService();
}

 */
