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

public class MemberApp {

  public static void main(String[] args) {
    //AppConfig appConfig = new AppConfig();
    //MemberService memberService = appConfig.memberService(); // 윗줄이랑 지금 해당되는 줄은 appConfig에서 필요할 때 직접 호출,

    어찌됐든.. 객체를 생성을 한 다음에 호출을 해야함. 이걸 스프링 방식으로 바꿔보자.
    스프링 방식으로 바꾸려면??

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class); // 이 코드의 뜻은 AppConfig 안에 있는 Bean 어노테이션인 애들을 스프링 빈으로 등록. 즉, 객체로 등록.
    근데 등록을 하더라도 반환값을 스프링 빈으로 등록하는것임. 빈의 이름은 메서드 이름.

    MemberService memberService = applicationContext.getBean("memberService", MemberService.class); // 빈들중에서 memberService라는애를 찾고 MemberService형으로 반환해라.



    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findById(1L);
    System.out.println("new member = " + member.getName());
    System.out.println("find Member = " + findMember.getName());
  }

}

public class MemberApp {

  public static void main(String[] args) {
    //AppConfig appConfig = new AppConfig();
    //MemberService memberService = appConfig.memberService();
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findById(1L);
    System.out.println("new member = " + member.getName());
    System.out.println("find member = " + findMember.getName());

  }

}





 */
