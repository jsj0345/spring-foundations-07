package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

  @Test
  void createOrder() {
    //OrderServiceImpl orderService = new OrderServiceImpl();
    //orderService.createOrder(1L, "itemA", 10000);
    /*
    위처럼 쓰면 안되는 이유가 지금 스프링 컨테이너에 암 것도 없음. 등록 조차 안되있는데 의존 관계 주입을 어떻게하나..

    아래 처럼 해보자. 생성자 주입 다시 살리자
     */
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    memberRepository.save(new Member(1L, "name", Grade.VIP));

    OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
    Order order = orderService.createOrder(1L, "itemA", 10000);
    assertThat(order.getDiscountPrice()).isEqualTo(1000);

    /*
    위 코드는 순수 자바 코드여서 스프링과는 관계가 없음.
    생성자 주입 방식에서 저장소와 할인정책을 주입 해야하기 때문에 직접 넣은 것.
     */
  }

}