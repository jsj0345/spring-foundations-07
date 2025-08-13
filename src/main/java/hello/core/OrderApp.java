package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class OrderApp {

  public static void main(String[] args) {

    //AppConfig appConfig = new AppConfig();
    //MemberService memberService = appConfig.memberService();
    //OrderService orderService = appConfig.orderService();

    // MemberService memberService = new MemberServiceImpl();
    // OrderService orderService = new OrderServiceImpl();

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    OrderService orderService = applicationContext.getBean("orderService", OrderService.class);


    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 20000);

    System.out.println(order);
    System.out.println("order.calculatePrice() = " + order.calculatePrice());

  }
}

/*
package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;

public class OrderApp {

  public static void main(String[] args) {
    //AppConfig appConfig = new AppConfig();
    //MemberService memberService = appConfig.memberService();
    //OrderService orderService = appConfig.orderService();

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.Class);
    OrderService orderService = applicationContext.getBean("orderService", OrderService.class);



    long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 10000);

    System.out.println("order = " + order);
  }

}


public class OrderApp {

  public static void main(String[] args) {
    // AppConfig appConfig = new AppConfig();
    // MemberService memberService = appConfig.memberSerivce();
    // OrderService orderService = appConfig.orderService();

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    자바에서 객체를 생성 한 다음에 객체 참조값을 통해 메서드나 멤버 변수에 접근 하는 것 처럼 스프링도 이러한 과정을 해줘야함.
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

    long memberId = 1L;
    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 10000);

    System.out.println(order);
  }

}

 */
