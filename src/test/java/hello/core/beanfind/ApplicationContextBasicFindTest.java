package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
getBean(이름, 클래스형)은 등록된 빈중에 같은 이름인 빈을 찾고 클래스형이거나 하위형이면 예외 안터짐.
getBean(클래스형) 이건 반환형이 클래스형이거나 하위형이면 예외 안터짐
 */

class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("빈 이름으로 조회")
  void findBeanByName() {
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class); // memberService가 MemberServiceImpl로 다운캐스팅이 가능한가? O.K
  }

  @Test
  @DisplayName("이름 없이 타입으로만 조회")
  void findBeanByType() {
    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  /*
  @Test
  @DisplayName("이름으로 조회")
  void findBeanByName1() {
    Object memberService = ac.getBean("memberService");
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

   */

  @Test
  @DisplayName("구체 타입으로 조회")
  void findBeanByName2() {
    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("빈 이름으로 조회X")
  void findBeanByNameX() {
    // ac.getBean("xxxx", MemberService.class);
    MemberService xxxx = ac.getBean("xxxxx", MemberService.class); // NoSuchBeanDefinitionException 예외 발생
    org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,
        () -> ac.getBean("xxxxx", MemberService.class)); // 오른쪽에 있는 로직을 실행하면 왼쪽에 있는 예외가 터져야함.
  }

}

/*
class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("빈 이름으로 조회")
  void findBeanByName() {
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl);
  }

  @Test
  @DisplayName("이름 없이 타입만으로 조회")
  void findBeanByType() {
    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("구체 타입으로 조회")
  void findBeanByName2() {
    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("빈 이름으로 조회X")
  void findBeanByNameX() {
    Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxxx", MemberService.class));
  }
}

class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplication(AppConfig.class);

  @Test
  @DisplayName("빈 이름으로 조회")
  void findBeanByName() {
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("이름 없이 타입만으로 조회")
  void findBeanByType() {
    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("구체 타입으로 조회")
  void findBeanByName2() {
    MemberService memberService = ac.getBean("memberService",MemberServiceImpl.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("빈 이름으로 조회X")
  void findBeanByNameX() {
    //ac.getBean("xxxxx", MemberService.class);

    Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxxx", MemberService.class));
  }

 }


___________________________________________________________________________________________

class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("빈 이름으로 조회")
  void findBeanByName() {
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
  }

  @Test
  @DisplayName("이름 없이 타입만으로 조회")
  void findBeanByType() {
    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
  }

  @Test
  @DisplayName("구체 타입으로 조회")
  void findBeanByName2() {
    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("빈 이름으로 조회X")
  void findBeanByNameX() {
    // ac.getBean("xxxxx", MemberService.class);
    Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxxx", MemberService.class));
  }

}

___________________________________________________________________________________________

스프링 빈 조회 - 기본

스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적인 조회 방법
getBean(빈이름, 조회)
getBean(타입)

만약에 조회 대상 스프링 빈이 없으면 예외 발생.
-> NoSuchBeanDefinitionException

class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("빈 이름으로 조회")
  void findBeanByName() {
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
  }

  @Test
  @DisplayName("이름 없이 타입만으로 조회")
  void findBeanByType() {
    MemberService memberService = ac.getBean(MemberService.class);
    assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("구체 타입으로 조회")
  void findBeanByName2() {
    MemberServiceImpl memberService = ac.getBean(MemberServiceImpl.class);
    Assertions.assertTht(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("빈 이름으로 조회X")
  void findBeanByNameX() {
    // ac.getBean("xxxxx", MemberService.class);
    Assertions.assertThrows(NosuchBeanDefinition.class, () -> ac.getBean("xxxxx", MemberService.class));
  }

}










 */
