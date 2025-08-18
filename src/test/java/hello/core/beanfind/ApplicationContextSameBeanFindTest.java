package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/*
getBean(클래스형) 빈을 보면 실제 객체와 반환형이 다를때가 있다. (물론 상속관계니까 상관없긴한데..)
메서드의 결과물은 "실제 객체"가아니라 반환형이다.

예를 들어
public MemberService memberService() {
  return new memberServiceImpl(new MemoryMemberRepository());
}
이런 메서드가 있으면 getBean에서 나오는 반환은 클래스형에 맞는 반환이나옴. 즉 , getBean(MemberService.class)면
MemberService형을 봄 구체적인 객체는 따로봄

그리고 클래스형이 호환이 안되는 난데없는 클래스형을 놓으면 오류 생김. 호환 가능한 빈을 조회.
 */

public class ApplicationContextSameBeanFindTest {
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
  void findBeanByTypeDuplicate() {
    org.junit.jupiter.api.Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    //오른쪽이 실행 됐을 때, 왼쪽 예외가 터져야함.
    //MemberRepository memberRepository = ac.getBean(MemberRepository.class);
    //Assertions.assertThat(memberRepository).isInstanceOf(MemberRepository.class);
  }

  @Test
  @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
  void findBeanByName() {
    MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
    org.assertj.core.api.Assertions.assertThat(memberRepository).isInstanceOf(MemberRepository.class);
  }

  @Test
  @DisplayName("특정 타입을 모두 조회하기")
  void findAllBeanByType() {
    Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
    /*
    MemberRepository 타입의 빈을 Map으로 반환.  key -> 빈 이름, value -> 빈 객체 인스턴스.
     */
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + ", value = " + beansOfType.get(key));
    }
    System.out.println("beansOfType = " + beansOfType);
    org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
  }

  @Configuration
  static class SameBeanConfig {

    @Bean
    public MemberRepository memberRepository1() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepository2() {
      return new MemoryMemberRepository();
    }

    /*
    이 코드의 문제점은 Bean을 등록 할 때, Bean이름은 다르지만 중요한건 Bean으로 등록 되는건 "반환 객체"다.
    객체가 같으니까 스프링 입장에선 뭔가 이상하다는걸 느낌.

    NoUniqueBeanDefinitionException이 발생 함.
    No qualifying bean of type 'hello.core.member.MemberRepository' available:
    expected single matching bean but found 2: memberRepository1,memberRepository2
     */


  }
}

/*
class ApplicationContextSameBeanFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();

  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
  void findBeanByTypeDuplicate() {
    //MemberRepository memberRepository = ac.getBean(MemberRepository.class);
    //Assertions.assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));

  }

  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
  void findBeanByName() {
   MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
   Assertions.assertThat(memberRepository).isInstanceOf(MemberRepository.class);
  }

  @Test
  @DisplayName("특정 타입을 모두 조회하기")
  void findAllBeanByType() {
    Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + " value = " + beansOfType.get(key));
    }

    System.out.println("beansOfType = " + beansOfType);
    Assertions.assertThat(beansOfType.size()).isEqualTo(2);
  }

  @Configuration
  static class SameBeanConfig {

    @Bean
    public MemberRepository memberRepository1() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepository2() {
      return new MemoryMemberRepository();
    }

  }
}


---------------------------------------------------------------------------------------------------------

class ApplicationContextSameBeanFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
  void findBeanByTypeDuplicate() {
    // MemberRepository bean = ac.getBean(MemberRepository.class);
    Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
  }

  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
  void findBeanByName() {
    MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
    assertThat(memberRepository).isInstanceOf(MemberRepository.class);
  }

  @Test
  @DisplayName("특정 타입을 모두 조회하기")
  void findAllBeanByType() {

    Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + ", value = " + beansOfType.get(key));
    }

    System.out.println("beansOfType = " + beansOfType);
    assertThat(beansOfType.size()).isEqualTo(2);
  }

  @Configuration
  static class SameBeanConfig {

    @Bean
    public MemberRepository memberRepository1() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepository2() {
      return new MemoryMemberRepository();
    }

  }

}

_______________________________________________________________________________

스프링 빈 조회 - 동일한 타입이 둘 이상

- 타입으로 조회시 같은 타입의 스프링 빈이 둘 이상이면 오류가 발생한다. 이때는 빈 이름을 지정하자.
- ac.getBeansOfType()을 사용하면 해당 타입의 모든 빈을 조회할 수 있다.

class ApplicationContextSameBeanFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();

  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
  void findBeanByTypeDuplicate() {
    // MemberRepository bean = ac.getBean(MemberRepository.class);
    assertThrows(NoUniqueBeanDefinitionException.class , () -> ac.getBean(MemberRepository.class));
  }

  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
  void findBeanByName() {
    MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
    assertThat(memberRepository).isInstanceOf(MemberRepository.class);
  }

  @Test
  @DisplayName("특정 타입을 모두 조회하기")
  void findAllBeanByType() {
    Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + ", value = " + beansOfType.get(key));
    }
    System.out.println("beansOfType = " + beansOfType);
    assertThat(beansOfType.size()).isEqualTo(2);
  }

  @Configuration
  static class SameBeanConfig {

    @Bean
    public MemberRepository memberRepository1() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepository2() {
      return new MemoryMemberRepository();
    }
  }

}








 */
