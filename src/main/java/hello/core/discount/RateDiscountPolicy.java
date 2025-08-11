package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

// 정률 할인 정책 구현체 구현
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


 */
