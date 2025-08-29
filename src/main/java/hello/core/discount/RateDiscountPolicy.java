package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// 정률 할인 정책 구현체 구현
@Component
//@Primary // @Primary를 쓰면 빈을 조회할때 하위형이 여러개가 있더라도 그 하위형들중에서 가장 우선순위로 호출.
// @Primary를 쓰면 빈을 조회할때 하위형이 여러개가 있더라도 그 하위형들중에서 가장 우선순위로 호출.
// @Qualifier("mainDiscountPolicy")
//@Qualifier("mainDiscountPolicy") // 만약에 Qualifier()안에 들어가는 문자열을 잘못 쓰면 컴파일 시점엔 못 잡을수도 있음.
// @MainDiscountPolicy // 여기서는 실수로 문자열을 잘못 쓰면 컴파일 시점에 오류를 바로 잡아줌.

/*
빈 등록시 @Qualifier를 붙여준다.
@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy {

}

@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy {

}
 */
@MainDiscountPolicy
//@MainDiscountPolicy RateDiscountPolicy의 구분자.
public class RateDiscountPolicy implements DiscountPolicy {

  private int discountPercent = 10;

  @Override
  public int discount(Member member, int price) {
    if (member.getGrade() == Grade.VIP) { // 열거형 상수 public static final Grade VIP = new Grade(); private 접근 제어자를 활용한 생성자.
      return price * discountPercent / 100 ;
    } else {
      return 0;
    }
  } // ctrl + shift + T를 누르면 테스트를 생성.

}

/*
public class RateDiscountPolicy implements DiscountPolicy {

  private int discountPercent = 10;

  @Override
  public int discount(Member member, int price) {
    if(member.getGrade() == Grade.VIP) {
      return price * discountPercent / 100;
    } else {
      return 0;
    }
  } // ctrl + shift + T를 누르면 테스트를 생성.

}

_____________________________________________________________________-

인터페이스를 만들어서 구현체만 작성해주면 원하는 클래스로 바꾸고 싶을때 부담이 없음!!

public class RateDiscountPolicy implements DiscountPolicy {

  //정률 할인 정책

  private int discountPercent = 10; // 10% 할인.

  @Override
  public int discount(Member member, int price) {

    if(member.getGrade() == Grade.VIP) {
      return price * discountPercent / 100;
    } else {
      return 0;
    }
 }

}


 */
