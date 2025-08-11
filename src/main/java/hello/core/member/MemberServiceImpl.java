package hello.core.member;

// 회원 서비스 구현체
public class MemberServiceImpl implements MemberService {

  //private final MemberRepository memberRepository = new MemoryMemberRepository();
  // ctrl+shift+enter를 하면 ; 이생김.

  private final MemberRepository memberRepository; // 추상화에만 의존.

  public MemberServiceImpl(MemberRepository memberRepository) {
    // 원래는 구체적인 클래스를 의존하는데 이제 memberRepository에 뭐가 들어올지 모름 결과적으로는 인터페이스만 의존.
    // 구체적인 클래스는 AppConfig만 알 수 있음 MemberServiceImpl은 알 길이 없음.
    this.memberRepository = memberRepository;
  }


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

package hello.core.member;

public class MemberServiceImpl implements MemberService {

  private fiinal MemberRepository memberRepository = new MemoryMemberRepository();

  -> DIP 원칙 못 지킴.
  DIP(의존 역전 원칙)
  -객체에서 어떤 Class를 참조해서 사용해야하는 상황이 생긴다면..
  그 Class를 직접 참조 하는것이 아니라 대상의 상위 요소(추상 클래스 or 인터페이스)로 참조.

  public void join(Member member) {
    memberRepository.save(member);
  }

  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }

}

 */
