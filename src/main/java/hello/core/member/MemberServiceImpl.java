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

  /*
  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
  이렇게 되면 MemberServiceImpl은 MemberRepository를 의존하지만 구현체가 뭐가 나올지를 모름
  그래서 DIP를 지킨 것.
  구현체의 결정은 "AppConfig"

  관심사의 분리: 객체를 생성하고 연결하는 역할과 실행하는 역할이 분리됐다.

  AppConfig -> 객체를 생성 및 연결

  MemberServiceImpl -> 구현체를 받아서 실행

  클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서
  DI(Dependency Injection)라고 부른다.
   */


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
