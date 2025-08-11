package hello.core.member;

//회원 서비스 인터페이스
public interface MemberService {

  void join(Member member); // 회원 가입.

  Member findMember(Long memberId); // 멤버 찾기(조회)

}

/*
package hello.core.member;

public interface MemberService {

  void join(Member member);

  Member findMember(Long memberId);

}

package hello.core.member;

public interface MemberService {

  void join(Member member);

  Member findMember(Long memberId);
}
 */
