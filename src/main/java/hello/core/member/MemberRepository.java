package hello.core.member;

public interface MemberRepository {

  void save(Member member);

  Member findById(Long memberId);


}

/*
package hello.core.member;

public interface MemberRepository {

  void save(Member member);

  Member findById(Long memberId);

}
 */
