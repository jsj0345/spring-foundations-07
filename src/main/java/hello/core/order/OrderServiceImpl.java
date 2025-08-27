package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
관심사의 분리

애플리케이션을 하나의 공연이라 생각해보자.
각각의 인터페이스를 배역(배우 역할)이라 하자.
그런데 이전 코드를 보면 배역에 맞는 배우를 선택하는 것은 누구인가?

즉, 인터페이스 -> 배역, 구현체 -> 배우 라고 생각하자.
이렇게 되면 배역 = 배우; 인데

사실 인터페이스는 배역만 책임지면 된다. 배우를 책임질 필요가 없음.

실제로도 배역을 누가 하느냐에 따라 소화만 하면 됨.

따라서, 역할이 있으면 그 역할에만 집중해야함.

기존 코드는 배우 역할이 배우를 지정하고 있으니까 웃긴것.

즉, 기획 개발자가 배우를 정해야 할 판에 배우 역할이 배우를 정한다? 이게 말이 안된다.

책임을 확실하게 분리해보자.
 */


// 주문 서비스 구현체
//@Component("Service")

// @RequiredArgsConstructor // ctrl + f12로 뭐가 만들어졌는지 확인해보자.
/*
final 같은 경우에는 값을 반드시 초기화 해줘야한다. (단 한번만 가능.)
그런데 이제 생성자를 통한 final 상수를 초기화 한다면.. 반드시 생성자에 초기화 할 때 필요로 하는 변수를 넣어줘야함.(의존관계 주입)
그래서 이름이 RequiredArgsConstructor 임!

여기서 @RequiredArgsConstructor 는

public OrderServiceImpl(MemberRepository memberRepository, DiscountService discountService) {
  this.memberRepository = memberRepository;
  this.discountService = discountService;
}
 */
@Component
public class OrderServiceImpl implements OrderService { // ctrl + shift + t 는 테스트를 만들 수 있다.


  /*
  private final MemberRepository memberRepository = new MemoryMemberRepository();
   */
  //private final MemberRepository memberRepository;
  //private final DiscountPolicy discountPolicy; // 인터페이스에만 의존.
  // final로 하는 이유는 무조건 초기화를 한번 해줘야함.

  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  // @Autowired private MemberRepository memberRepository;
  /*
  @Autowired private DiscountPolicy discountPolicy; -> 필드 주입은 별로 안좋음.

  왜 안 좋을까? 나중에 주입 받는걸 변경하고 싶을때, 변경하기가 힘들다.

  필드 주입 방식에서는 @Primary, @Qualifier 같은 스프링 전용 애노테이션을 클래스에 계속 붙여줘야 함.

  또는 아예 클래스를 열어서 필드 선언부를 수정해야 함.
  → AppConfig 같은 외부 설정에서 제어할 수 없음.
   */

  /*
  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  인터페이스에만 의존하도록 바꿈.

  근데 이렇게 되면 구현체가 없으니까 실행 불가 null로 초기화될테니.

  이 문제를 해결하려면 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy 의 구현 객체를 대신 생성하고 넣어줘야함.
   */

  /*
  @Autowired
  public void setMemberRepository(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Autowired
  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
  }

   */

  /*
  AppConfig에서 알아서 구체적으로 주입을 하니까 OrderServiceImpl은 내부에 있는 메서드 실행 이런거나 잘하면 된다.
  뭐가 주입 될지 신경 쓸 필요가 없다.
   */

   // 만약에 생성자가 1개만 있으면 @Autowired가 없어도 자동으로 연결해준다. (@Autowired가 있다는 뜻)
  //public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
  //  System.out.println("memberRepository = " + memberRepository);
  //  System.out.println("discountPolicy = " + discountPolicy);
  //  System.out.println("생성자 주입");
  // this.memberRepository = memberRepository;
  //  this.discountPolicy = discountPolicy;

    /*
    생성자 주입 같은 경우에는 생각을 해보면 어찌됐든 OrderServiceImpl이라는 객체를 만들어야 스프링에서 쓸수 있음.
    근데 객체를 만들때 당연히 생성자를 만들어야함. (호출을 해야함!)
    이러한 이유로 생성자 주입을 할 때는 자동으로 의존관계 주입이 일어남.
     */
  //} // 역할과 책임을 적절하게 분리함. 원래 구체적인 클래스를 뭘 써야 할지 봤어야 했는데 그럴 필요 없어짐. (SRP를 지킴)
  @Autowired
  public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;

    // @Qualifier("mainDiscountPolicy") 한번 써보기 매개변수에 쓰면 된다.
    /*
    (MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) 를 보면
    @Autowired를 할 때, 형태에 맞게 연결을 해준다. (의존성 주입)
    그런데.. DiscountPolicy의 하위형은 RateDiscount, FixDiscount가 있다.
    이러면 @Component가 두개 이므로 오류가 발생한다.

    구체적인 의존을 해야하나? 라는 생각이 들겠지만 아니다.
    주입을 할 때, 이름을 보고 하기 때문에 discountPolicy를 rateDiscountPolicy로 바꿔보자.

    전에 컴포넌트 스캔을 할 때, 맨앞에 대문자는 소문자로 바꾸는 특성이 있기 때문.

    @Autoriwed 필드 명 매칭
    -> @Autowired는 타입 매칭을 시도하고, 이때 여러 빈이 있으면 필드 이름, 파라미터 이름으로 빈 이름을 추가 매칭한다.
     */
  }

  /*
  @Autowired
  public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }
   */




  /*
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  여기서 만약에 DiscountPolicy를 구현하는 RateDiscountPolicy를 적용하려면?

  -> private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); 로 바꿔야한다.
  이러면 클라이언트에서 코드를 바꿔야한다. (의존성이 있다는 것.)

  -> 개방-폐쇄 원칙(Open/Closed Principle)을 지키지 않음.
  소포트웨어 구성 요소는 확장에 대해 열려 있어야 하지만, 변경에 대해서는 닫혀 있어야 한다.
  즉, 기존 코드를 수정하지 않고도 새로운 기능을 추가할 수 있도록 설계해야 한다는 원칙임.

  그리고 더군다나 DIP를 지키려면 인터페이스 or 추상 클래스에 의존해야 하는데 구체적인 클래스에 의존해버려서 DIP X.

   */



  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId); // 회원 조회 (등급)
    int discountPrice = discountPolicy.discount(member, itemPrice); // 조회한 정보로 등급 보기

    /*
    단일 책임 원칙(Single Responsibility Principle, SRP)은 소프트웨어 설계 원칙 중 하나로,
    각 클래스나 모듈은 하나의 책임만 가져야 한다는 것을 의미함.

    구체적인 계산 로직을 몰라도 할인 정책 인터페이스를 통해서만 결과를 받아오는 설계임. (SRP)


     */
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

  /*
  주문 생성 요청이 오면, 회원 정보를 조회하고, 할인 정책을 적용한 다음 주문 생성 객체를 반환한다.
  메모리 회원 리포지토리와 고정 금액 할인 정책을 구현체로 생성한다.

  위 메서드를 보면 주문 서비스 객체는 회원 정보와 할인 가격만 관심이 있지 내부 관심이없음.
  이러한 이유로 인터페이스나 추상 클래스에만 의존하는게 좋음.
   */

  // 테스트 용도 (싱글톤이 적용되는지를 보기 위함.)
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }

  /*
  @Autowired // 메서드 주입 (한번에 여러 필드를 주입 받을 수 있음, 잘 사용하진 않음)
  public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

   */


  /*
  // 테스트 용도

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }

  */
}

/*
package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository = new MemoryMemberRepository();
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

}

public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository = new MemoryMemberRepository();
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {

    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

}

public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository = new MemoryMemberRepository();

  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  // 구체적인 클래스에 의존함 DIP를 준수하지 못함. 또한 주문서비스는 주문에 관한것만 책임 져야하는데 무슨 할인정책을 갖고와야하는지 등을 신경써야함.
  즉, SRP도 안지킴. DIP란? (Dependency Inversion Principle - 의존성 역전 원칙)
  SRP는 (Single Responsibility Principle)

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

}

public class OrderServiceImpl implements OrderService {

  // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
  이렇게 고치면 코드 내부를 고칠 것 없어서 편하다.

  하지만 여기서 중요한건 너무 구체적으로 참조해서 클라이언트에서 다른 정율정책을 필요로 할때 마다 객체 생성을 할 때, 이름을 바꿔줘야한다.

  이러면 좀 난감하다. 구체적 의존으로 인해 DIP를 못 지킴.

  그리고 좋은 객체 지향 프로그래밍은 확장에는 개방적이여야 하나 코드를 변경하는거에는 폐쇄적이여야한다.

  근데 위 코드를 보면 변경하는거에 폐쇄적이지 않음. (OCP 원칙 준수 X 동시에 SRP도 준수하지않음.)

  클라이언트 코드를 고치지 않고 확장만 하려고하면 인터페이스에만 의존 해야함. 방법을 찾아보자.
}

public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

}

생성자 주입 방식 + 불변을 사용하려면
일단 멤버변수가 final로 선언 되야 한다.

final로 선언 하면 무조건 멤버 변수를 초기화 해줘야 한다
초기화 방법은 직접 초기화를 하거나 생성자를 통한 초기화 이렇게 두가지만 가능하다.

그런데 보통 스프링에서 DIP를 준수하려면 구체 클래스에 의존하면 안된다.
그래서 생성자에 인터페이스형을 넣고 구체 클래스를 따로 넣어줘야한다.

근데 이러한 과정을 생략하려면 롬복을 활용해서 @RequiredArgsConstructor를 쓰면 된다.

@Component
public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  @Autowired
  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

}

수정자 주입(setter 주입)

-setter라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입하는 방법이다.
- 선택, 변경 가능성이 있는 의존관계에 사용

@Component
public class OrderServiceImpl implements OrderService {

  private MemberRepository memberRepository;
  private DiscountPolicy discountPolicy;

  @Autowired
  public void setMemberRepository(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Autowired
  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
  }

}

필드 주입

- 이름 그대로 필드에 바로 주입하는 방법이다.
- 코드가 간결해서 많은 개발자들을 유혹하지만 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.
- DI 프레임워크가 없으면 아무것도 할 수 없다.

@Component
public class OrderServiceImpl implements OrderService {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private DiscountPolicy discountPolicy;

}

일반 메서드 주입

- 일반 메서드를 통해서 주입 받을 수 있다.
- 일반적으로 잘 사용하지 않는다.

@Component
public class OrderServiceImpl implements OrderService {

  private MemberRepository memberRepository;
  private DiscountPolicy discountPolicy;

  @Autowired
  public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

}

옵션 처리

자동 주입 대상을 옵션으로 처리하는 방법은 다음과 같다.

- @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 X.
- org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력된다.
- Optional<> : 자동 주입할 대상이 없으면 Optional.empty가 입력된다.

// 호출 안됨
@Autowired(required = false)
public void setNoBean1(Member member) {
  System.out.println("setNoBean1 = " + member);
}

// null 호출
@Autowired
public void setNoBean2(@Nullable Member member) {
  System.out.println("setNoBean2 = " + member);
}

//Optional.empty 호출
@Autowired
public void setNoBean3(Optional<Member> member) {
  System.out.println("setNoBean3 = " + member);
}


 */
