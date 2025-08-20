package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
		// 이거 실행하면 문구가 잘 안뜸. 실행하고 나서 터미널에 .\gradlew bootRun --stacktrace --info 이거 그대로 입력
	}

}
