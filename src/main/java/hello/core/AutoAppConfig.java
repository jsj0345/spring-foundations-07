package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/*
@ComponentScan은 @Component 계열 애노테이션이 붙은 클래스들을 자동으로 찾아서 스프링 빈으로 등록함.
@Bean은 클래스가 아니라 메서드 단위에서 동작함.

즉, @Bean은 @ComponentScan으로는 빈 등록을 못함.

메서드를 빈으로 등록하려면 @Bean도 달아야함. 그리고 스프링 컨테이너에서 빈으로 등록 해주려면
AnnotationConfigApplicationContext가 필요

@Component 빈 → 기본적으로 항상 싱글톤(스코프 바꾸지 않는 한)

@Configuration + @Bean → 싱글톤 안전(메서드 간 호출도 OK)

@Component + @Bean(라이트 설정) → 메서드 간 직접 호출 시 새 인스턴스 생길 위험 → 권장 X

____________________________________________________________________________________

@ComponentScan에서 basePackages를 따로 지정 할 것 없이 설정 정보 클래스의 위치(ex) AppConfig)를 프로젝트 최상단에 두는 것이다.
최근에 스프링 부트도 이 방법을 기본으로 제공한다.


 */

@Configuration
@ComponentScan (
    //basePackages = "hello.core.member", // hello.core.member 패키지 안에 있는 것만 컴포넌트 스캔 대상이 된다.
    //basePackageClasses = AutoAppConfig.class, // AutoAppConfig가 속한 패키지안에 있는 컴포넌트들을 찾음. (즉, hello.core 패키지 안에 있는 컴포넌트들을 찾음.)
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
    // FilterType.ANNOTATION -> 애노테이션 기준으로 필터링.
    // 이것의 의미는 컴포넌트 스캔을 하는 대신 Configuration이 붙은 클래스는 컴포넌트 스캔하지 말라는 뜻. (즉, 스프링 빈으로 등록 X)
    // AppConfig에 @Configuration이 “메서드까지 빈으로 등록한다”는 잘못된 표현이고,
    // 올바른 설명은 @Bean 메서드가 반환하는 객체를 빈으로 등록하며 싱글톤을 유지하도록 돕는다”
    // @ComponentScan은 @Configuration을 살펴보면 @Component가 있음. 이런걸 스프링 빈으로 등록.
    // @Configuration은 내부의 @Bean 메서드 반환 객체들을 스프링 컨테이너에 빈으로 등록하고, 싱글톤을 보장하도록 도와준다.
    // 코드를 실행했을 때 AnnotationConfigApplicationContext 같은 컨테이너가 있어야 @Configuration의 효능이 발휘된다

    /*
    만약에 basePackages, basePackageClasses를 지정하지 않으면? AutoAppConfig에 속한 패키지와 하위 패키지들에 있는 컴포넌트들을 찾음.
     */
)

// @ComponentScan 애노테이션은 @Component 애노테이션이 붙은 클래스를 찾아서 다 스프링 빈으로 등록.
public class AutoAppConfig {

  /*
  @Bean(name = "memoryMemberRepository") // 빈을 수동으로 등록
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
  */

  //-> 이대로 돌려도 AppConfig에 있는 memberRepository 빈 이름과 중복된다.
  // */

  /*
  @Component를 보면 MemoryMemberRepository가 있다. 근데 맨앞에 알파벳은 대문자가 아닌 소문자로 바뀐다. (memoryMemberRepository)

  그럼 AutoAppConfig에 있는 @Bean과 memoryMemberRepository 빈의 이름은 서로 같다. 그럼 중복 문제가 발생함.

  테스트 코드를 실행해보자.
  */
}

/*
@Configuration
@ComponentScan (
  excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
  // 이것의 의미는 컴포넌트 스캔을 하는 대신 Configuration이 붙은 클래스는 컴포넌트 스캔하지 말라는 뜻. (즉, 스프링 빈으로 등록 X)
  // AppConfig에 @Configuration이 붙어 있어서 다 빈으로 등록한다.
  // @ComponentScan은 @Configuration을 살펴보면 @Component가 있음. 이런걸 스프링 빈으로 등록.
}

// @ComponentScan 애노테이션은 @Component 애노테이션이 붙은 클래스를 찾아서 다 스프링 빈으로 등록.
public class AutoAppConfig {

}

@Configuration
@ComponentScan(
  excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {


}

@Configuration
@ComponentScan(
  basePackages = "hello.core" ,
  basePackageClasses = "AutoAppConfig.class" ,
  excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)

public class AutoAppConfig {

}
 */
