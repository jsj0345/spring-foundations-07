package hello.core.scan.filter;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.springframework.context.annotation.ComponentScan.*;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

  @Test
  void filterScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
    BeanA beanA = ac.getBean(BeanA.class);
    assertThat(beanA).isNotNull();

    assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(BeanB.class)); // 당연히 스캔이 안되니까 예외가 터짐.
  }

  @Configuration
  @ComponentScan(
      includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class) , // 컴포넌트 스캔 O
      excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class) // 컴포넌트 스캔 X
  )
  static class ComponentFilterAppConfig {

  }
}

/*
public class ComponentFilterAppConfigTest {

  @Test
  void filterScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
    BeanA beanA = ac.getBean(BeanA.class);
    assertThat(beanA).isNotNull();

    assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(BeanB.class));
  }

  @Configuration
  @ComponentScan(
    includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
    excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
  )

  static class ComponentFilterAppConfig {

  }

}

_________________________________________________________________________________________________________-

public class ComponentFilterAppConfigTest {

  @Test
  void filterScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.Class);

    BeanA beanA = ac.getBean("beanA", BeanA.class);
    Assertions.assertThat(beanA).isNotNull();

    Assertions.assertThrows(
      NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", BeanB.class);
  }

  @Configuration
  @ComponentScan(
    includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
    excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
  )

  static class ComponentFilterAppConfig {

  }


}




}
 */