package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// 같은 멤버인지를 확인하기 위한 테스트
public class MemberServiceTest {
 
  MemberService memberService;

  @BeforeEach // 테스트 실행전에 무조건 실행됨.
  public void beforeEach() {
    AppConfig appConfig = new AppConfig();
    memberService = appConfig.memberService();
  }

  /*
  의존성 역전 원칙(DIP : Dependency Inversion Principle)

  고차원 모듈은 저차원 모듈에 의존하면 안 된다. 이 두 모듈 모두 다른 추상화된 것에
  의존해야 한다.

  추상 클래스나 인터페이스에 의존해야 한다라는 의미.

  인터페이스의 구현체가 하나만 있으면 ~impl이라는 단어를 붙임.

  스프링 입문 강의에서 이런 걸 공부한 적이 있음.

  @Controller
  public class MemberController {

    private final MemberService memberService;


    public MemberController(MemberService memberService) {
      this.memberService = memberService;
    }
  ....

  이걸 보면 MemberController는 memberService에 의존을 함.

  그러면 DIP원칙을 지켰나? OK

  왜냐면 인터페이스에 의존을 하는거지 구현체에 의존 하는게 아님.
   */

  @Test
  void join() {
    //given
    Member member = new Member(1L,"memberA",Grade.VIP);

    //when

    memberService.join(member);

    Member findMember = memberService.findMember(1L);

    //then
    Assertions.assertThat(member).isEqualTo(findMember);
  }
}

/*
package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

  MemberService memberService = new MemberServiceImpl();

  @Test
  void join() {
    //given
    Member member = new Member(1L, "memberA", Grade.VIP);

    //when
    memberService.join(member);
    Member findMember = memberService.findMember(1L);

    //then
    Assertions.assertThat(member).isEqualTo(findMember);

  }

}


package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

  MemberService memberService = new MemberServiceImpl();

  @Test
  void join() {
    //given
    Member member = new Member(1L, "memberA", Grade.VIP);

    //when
    memberService.join(member);
    Member findMember = memberService.findMember(1L);

    //then
    Assertions.assertThat(member).isEqualTo(findMember);
  }

}
 */