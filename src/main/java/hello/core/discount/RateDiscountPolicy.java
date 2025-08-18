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
