package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

  /**
   * @return 할인 대상 금액
   */
  int discount(Member member, int price); // F2는 오류 난 곳으로 이동.
}

/*
package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

  @return 할인 대상 금액

  int discount(Member member, int price); // F2는 오류 난 곳으로 이동.

  -> discount에 Member를 넣는 이유는 Grade를 알아야하니까. Grade에 관한 정보가 있으니까.

}
 */
