package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

  public static void main(String[] args) {
    //AppConfig appConfig = new AppConfig();
    //MemberService memberService = appConfig.memberService();
    // MemberService memberService = new MemberServiceImpl(); 직접 생성 해줘야하는데 이럴 필요 X

    /*
    원래 기존에는 appConfig에서 멤버서비스를 호출해서 직접 꺼내옴.
    이제는 스프링 방식으로 바꿔야함.

    즉, 스프링 컨테이너에 객체들이 있으니 컨테이너에서 꺼내와야 한다!
     */


    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

    /*
    스프링 객체들을 관리해줌.
    AppConfig에 있는 환경 설정 정보를 가지고 스프링이 AppConfig 안에 있는
    @Bean 들을 다 스프링 컨테이너에 넣어줌.

    getBean(a,b)라고 하면 a에 대한 객체를 찾는다는 의미. b는 반환 타입.
    a는 key b는 value인 느낌.

    */


    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findMember(1L);
    System.out.println("new member = " + member.getName());
    System.out.println("find Member = " + findMember.getName());

    // 이렇게 main 메서드로 애플리케이션 로직을 테스트 하는 것은 좋지 않다. test 파일을 활용. (JUnit 테스트를 활용하자.)
  }

}

/*
package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;

public class MemberApp {

  public static void main(String[] args) {
    //AppConfig appConfig = new AppConfig();
    //MemberService memberService = appConfig.memberService();

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findMember(1L);
    System.out.println("new member = " + member.getName());
    System.out.println("find Member = " + findMember.getName());
 }

}



 */
