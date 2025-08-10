package hello.core.member;

public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository = new MemoryMemberRepository(); // ctrl+shift+enter를 하면 ; 이생김.


  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }
}

/*
package hello.core.member;

public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository =  new MemoryMemberRepository();

  public void join(Member member) {
    memberRepository.save(member);
  }

  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }


}
 */
