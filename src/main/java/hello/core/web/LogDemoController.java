package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

  private final LogDemoService logDemoService;
  // private final ObjectProvider<MyLogger> myLoggerProvider; // CoreApplication을 실행 하면 오류가 난다.
  private final MyLogger myLogger;

  /*
  Scope 'request' is not active for the current thread;
  이러한 이유는 MyLogger라는 것은 HTTP 요청이 들어 올 때, 빈이 생성이 되는데 들어오지도 않았으니 생성 됐을리가 없음.
  그래서 빈을 못 찾아냄.
   */

  @RequestMapping("log-demo")
  @ResponseBody
  public String logDemo(HttpServletRequest request) throws InterruptedException {
    String requestURL = request.getRequestURL().toString(); // 고객이 어떤 URL로 요청 했는지 알 수 있음.
    //MyLogger myLogger = myLoggerProvider.getObject(); // 이때 MyLogger 리퀘스트 빈 스코프가 생성. (@PostConstruct를 실행)

    System.out.println("myLogger = " + myLogger.getClass());
    // myLogger = class hello.core.common.MyLogger$$SpringCGLIB$$0
    myLogger.setRequestURL(requestURL);
    myLogger.log("controller test");
    // Thread.sleep(1000);
    logDemoService.logic("testId");

    return "OK";
  }

  /*
  url을 입력하고나서 @RequestMapping 아래에 있는 메서드를 실행.

  원래 리퀘스트 빈 스코프는 생성이 안되지만 ObjectProvider를 이용하면 스프링 컨테이너에서 요청중에 갖고옴 그러면 HTTP 요청이란 조건도 맞으니

  리퀘스트 빈 스코프는 성공적으로 생존. 이거를 이용해서 코드를 돌림.
   */


}

/*
@Controller
@RequiredArgsConstructor
public class LogDemoController {

  private final LogDemoService logDemoService;
  private final MyLogger myLogger;

  @RequestMapping("log-demo")
  @ResponseBody
  public String logDemo(HttpServletRequest request) {
    String requestURL = request.getRequestURL().toString();
    myLogger.setRequestURL(requestURL);

    myLogger.log("controller test");
    logDemoService.logic("testId");

    return "OK";
  }

}

@Controller
@RequiredArgsConstructor
public class LogDemoController {

  private final LogDemoService logDemoService;
  private final MyLogger myLogger;

  @RequestMapping("log-demo")
  @ResponseBody
  public String logDemo(HttpServletRequest request) {
    String requestURL = request.getRequestURL().toString();
    myLogger.setRequestURL(requestURL);

    myLogger.log("controller test");
    logDemoService.logic("testId");

    return "OK";
  }

}

 */
