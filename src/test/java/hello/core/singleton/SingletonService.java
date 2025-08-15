package hello.core.singleton;

public class SingletonService {

  private static final SingletonService instance = new SingletonService();

  /*
  static 키워드는 코드가 실행 되면 일단 먼저 메모리에 올라감.

  그때 객체가 생성된거임. 그래서 객체는 딱 한번만 생성된다. 라는거지.
   */

  public static SingletonService getInstance() {
    return instance;
  }

  private SingletonService() {

  }

  public void logic() {
    System.out.println("싱글톤 객체 로직 호출");
  }

}
