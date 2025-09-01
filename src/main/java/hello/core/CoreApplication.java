package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
		// 이거 실행하면 문구가 잘 안뜸. 실행하고 나서 터미널에 .\gradlew bootRun --stacktrace --info 이거 그대로 입력
	}

	/*
	implementation 'org.springframework.boot:spring-boot-starter-web'
	build.gradle에 위에 것을 추가해서 콘솔 창에 다음과 같이 나옴.
	17:21:06.140 [main] INFO  o.s.b.w.e.tomcat.TomcatWebServer -- Tomcat initialized with port 8080 (http)

	콘솔 창에서 키워드를 찾고 싶으면 ctrl + f
	 */

}
