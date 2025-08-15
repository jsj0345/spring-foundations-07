# 스프링 용어 정리

## 1. IoC (Inversion of Control)

- 기존 프로그램은 클라이언트 구현 객체가 스스로 필요한 서버 구현 객체를 생성하고 연결하고 실행했다.

구현 객체가 프로그램의 제어 흐름을 스스로 조종했다. 개발자 입장에서는 자연스로운 흐름이다.

- 반면에 AppConfig가 등장한 이후에 구현 객체는 자신의 로직을 실행하는 역할만 담당한다. 프로그램의 제어 흐름은
AppConfig가 가져간다. 예를 들어서 OrderServiceImpl은 필요한 인터페이스들을 호출하지만 어떤 구현 객체들이 실행될지 모른다.

**예시 코드**
public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) { 
  this.memberRepository = memberRepository;
  this.discountPolicy = discountPolicy;
}

- 프로그램에 대한 제어 흐름에 대한 권한은 모두 AppConfig가 가지고 있다. 심지어 OrderServiceImpl도
AppConfig가 생성한다. 그리고 AppConfig는 OrderServiceImpl이 아닌 OrderService 인터페이스의
다른 구현 객체를 생성하고 실행할 수도 있다. 그런 사실도 모른체 OrderServiceImpl은 묵묵히 자신의 로직을 실행할 뿐이다.

**예시 코드**
public class AppConfig { // 애플리케이션 전체를 설정하고 구성한다는 의미로 지음.

public MemberService memberService() { // 생성자 주입 방식
  return new MemberServiceImpl(memberRepository());
}

private MemberRepository memberRepository() {
  return new MemoryMemberRepository(); // 중복 방지 (역할이 매우 명확함)
}

public OrderService orderService() {
  return new OrderServiceImpl(memberRepository(), discountPolicy());
}

private DiscountPolicy discountPolicy() {
  return new RateDiscountPolicy();
}

}

- 이렇듯 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IoC)이라 한다. 


## 2. 프레임워크 vs 라이브러리

- 프레임워크가 내가 작성한 코드를 제어하고, 대신 실행하면 그것은 프레임워크가 맞다. (Junit)

- 반면에 내가 작성한 코드가 직접 제어의 흐름을 담당한다면 그것은 프레임워크가 아니라 라이브러리다. 

**예시**
- 웹에서 버튼을 클릭하면 다른곳으로 들어가지는건 GetMapping을 이용함. GetMapping은 내가 지정하고
스프링이 보고 실행하는것. 

## 3. 의존관계 주입 DI(Dependency Injection)

- OrderServiceImpl은 DiscountPolicy 인터페이스에 의존한다. 실제 어떤 구현 객체가 사용될지는 모른다. 

- 의존관계는 정적인 클래스 의존 관계와 , 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계 둘을 분리해서 생각해야 한다.

**예시 코드**
public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
  this.memberRepository = memberRepository;
  this.discountPolicy = discountPolicy;
} 

당장 이 코드만 봐도 MemberRepository , DiscountPolicy 인터페이스를 의존 한다는 것은 눈으로도 볼 수 있음(정적 의존 관계)
근데 구현체는 사실 실행 할 때 주입 되기 때문에 이럴땐 동적 의존 관계임. 


## 4. IoC 컨테이너, DI 컨테이너

- AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 IoC 컨테이너 또는 DI 컨테이너라 한다. 

- 의존관계 주입에 초점을 맞추어 최근에는 주로 DI 컨테이너라 한다.


## 5. 스프링 컨테이너

- ApplicationContext를 스프링 컨테이너라 한다. (ApplicationContext는 인터페이스다.)

- 기존에는 개발자가 AppConfig를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제부터 스프링 컨테이너를 통해서 사용한다.

- 스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정(구성) 정보로 사용한다.
여기서 @Bean이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다. 
이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다. 

- 스프링 빈은 @Bean이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다. (memberService, orderService)

- 이전에는 개발자가 필요한 객체를 AppConfig를 사용해서 직접 조회했지만, 이제부터는 스프링 컨테이너를 통해서
필요한 스프링 빈(객체)를 찾아야 한다. 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있다.

- 기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제부터는 스프링 컨테이너에
스프링 빈으로 등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다. 

- AnnotationConfigApplicationContext는 ApplicationContext를 implements함. 

**오해 하지 말 것**
다음 예시 코드를 보자.
@Bean
public MemberService memberService() { // 생성자 주입 방식
  return new MemberServiceImpl(memberRepository());
}

이 코드 같은 경우에는 반환형은 MemberService(참조형)이지만 
실제 반환은 MemberServiceImpl임. 따라서, 빈으로 등록되는건 return 다음임. 

## 6. 빈 등록

- 주의 : 빈 이름은 항상 다른 이름을 부여해야 한다. 같은 이름을 부여하면, 다른 빈이 무시되거나, 기존 빈을 덮어버리거나
설정에 따라 오류가 발생한다. 

- 위에서 "오해 하지 말 것"에서 설명 했듯이, 반환형이 Bean으로 등록되는거고 Bean의 이름은 "메서드 이름". 

## 7. BeanFactory와 ApplicationContext

BeanFactory 

- 스프링 컨테이너의 최상위 인터페이스다.
- 스프링 빈을 관리하고 조회하는 역할을 담당한다.
- getBean()을 제공한다.
- 지금까지 배운 범위에서 사용했던 대부분의 기능은 BeanFactory가 제공하는 기능이다. (ex. getBean())

ApplicationContext

- BeanFactory 기능을 모두 상속 받아서 제공한다.
- 빈을 관리하고 검색하는 기능을 BeanFactory가 제공해주는데, 그러면 둘의 차이가 뭘까?
- 애플리케이션을 개발할 때는 빈을 관리하고 조회하는 기능은 물론이고, 수 많은 부가 기능이 필요함.

ApplicationContext가 제공하는 부가기능

ApplicationContext는 MessageSource, EnvironmentCapable, ApplicationEventPublisher, ResourceLoader를 상속 받음. 

- MessageSource : 메시지 소스를 활용한 국제화 기능 ex) 한국에서 들어오면 한국어, 영어권에서 들어오면 영어
- EnvironmentCapable : 로컬,개발,운영등을 구분해서 처리.
- ApplicationEventPublisher : 이벤트를 발행하고 구독하는 모델을 편리하게 지원.
- ResourceLoader : 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회.

## 8. 스프링 빈 설정 메타 정보 - BeanDefinition

- 스프링은 자바 코드, XML 파일을 읽어서 빈을 등록한다. 이런걸 보면 다양한 것들을 읽는다. 어떻게 읽는걸까?

코드를 한번 보자.

**예시 코드**
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {
  private final AnnotatedBeanDefinitionReader reader;
  private final ClassPathBeanDefinitionScanner scanner;
  
  ....

}

코드의 일부를 따왔다. 여기서 보면 reader라는 변수로 AppConfig.class를 읽고 BeanDefintion을 생성한다. 

**예시 코드2**
public class GenericXmlApplicationContext extends GenericApplicationContext {
  private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

   ...
}

여기서도 reader를 통해 BeanDefiniton을 생성. 

BeanDefinition에는 많은 정보들이 있음. 암튼 reader를통해 자바 코드, XML 파일을 읽어서 빈의 메타 정보를 알려줌. 






