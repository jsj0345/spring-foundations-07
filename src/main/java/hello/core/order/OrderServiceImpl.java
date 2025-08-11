package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

// 주문 서비스 구현체
public class OrderServiceImpl implements OrderService {


  /*
  private final MemberRepository memberRepository = new MemoryMemberRepository();


   */
  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy; // 인터페이스에만 의존.
  // final로 하는 이유는 무조건 초기화를 한번 해줘야함.


  /*
  AppConfig에서 알아서 구체적으로 주입을 하니까 OrderServiceImpl은 내부에 있는 메서드 실행 이런거나 잘하면 된다.
  뭐가 주입 될지 신경 쓸 필요가 없다.
   */
  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  } // 역할과 책임을 적절하게 분리함. 원래 구체적인 클래스를 뭘 써야 할지 봤어야 했는데 그럴 필요 없어짐. (SRP를 지킴)

  /*
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  여기서 만약에 DiscountPolicy를 구현하는 RateDiscountPolicy를 적용하려면?

  -> private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); 로 바꿔야한다.
  이러면 클라이언트에서 코드를 바꿔야한다. (의존성이 있다는 것.)

  -> 개방-폐쇄 원칙(Open/Closed Principle)을 지키지 않음.
  소포트웨어 구성 요소는 확장에 대해 열려 있어야 하지만, 변경에 대해서는 닫혀 있어야 한다.
  즉, 기존 코드를 수정하지 않고도 새로운 기능을 추가할 수 있도록 설계해야 한다는 원칙임.

  그리고 더군다나 DIP를 지키려면 인터페이스 or 추상 클래스에 의존해야 하는데 구체적인 클래스에 의존해버려서 DIP X.

   */



  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId); // 회원 조회 (등급)
    int discountPrice = discountPolicy.discount(member, itemPrice); // 조회한 정보로 등급 보기

    /*
    단일 책임 원칙(Single Responsibility Principle, SRP)은 소프트웨어 설계 원칙 중 하나로,
    각 클래스나 모듈은 하나의 책임만 가져야 한다는 것을 의미함.

    구체적인 계산 로직을 몰라도 할인 정책 인터페이스를 통해서만 결과를 받아오는 설계임. (SRP)


     */
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

  /*
  주문 생성 요청이 오면, 회원 정보를 조회하고, 할인 정책을 적용한 다음 주문 생성 객체를 반환한다.
  메모리 회원 리포지토리와 고정 금액 할인 정책을 구현체로 생성한다.

  위 메서드를 보면 주문 서비스 객체는 회원 정보와 할인 가격만 관심이 있지 내부 관심이없음.
  이러한 이유로 인터페이스나 추상 클래스에만 의존하는게 좋음.
   */
}

/*
package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository = new MemoryMemberRepository();
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

}

public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository = new MemoryMemberRepository();
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {

    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

}



 */
