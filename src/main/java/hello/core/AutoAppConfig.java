package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan (
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
    // 이것의 의미는 컴포넌트 스캔을 하는 대신 Configuration이 붙은 클래스는 컴포넌트 스캔하지 말라는 뜻. (즉, 스프링 빈으로 등록 X)
    // AppConfig에 @Configuration이 붙어 있어서 다 빈으로 등록된다.
    // @ComponentScan은 @Configuration을 살펴보면 @Component가 있음. 이런걸 스프링 빈으로 등록.
)

// @ComponentScan 애노테이션은 @Component 애노테이션이 붙은 클래스를 찾아서 다 스프링 빈으로 등록.
public class AutoAppConfig {
}
