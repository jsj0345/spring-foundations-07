package hello.core.member;

// 회원 저장소 인터페이스
public interface MemberRepository {

  void save(Member member);

  Member findById(Long memberId);


}

/*
package hello.core.member;

public interface MemberRepository {

  void save(Member member); // 삽입

  Member findById(Long memberId); // 조회

}
 */
