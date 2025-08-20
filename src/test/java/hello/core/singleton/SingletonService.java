package hello.core.singleton;


// 싱글톤 패턴

/*
싱글톤 패턴은 자바 중급 1편에서 ENUM(열거형 상수)에서 쓰는 논리와 비슷하다.
확인 한번 해보자.
 */

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

  /*
  1. static 영역에 객체 instance를 미리 하나 생성해서 올려둔다.

  2. 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다.
  이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.

  3. 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서 혹시라도 외부에서
  new로 객체 인스턴스가 생성되는 것을 막는다.
   */
}

/*
package hello.core.singleton;

public class SingletonService {

  //1. static 영역에 객체를 딱 1개만 생성해둔다.
  private static final SingletonService instance = new SingletonService();

  //2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.
  public static SingletonService getInstance() {
    return instance;
  }

  //3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 한다.
  private SingletonService() {

  }

  public void logic() {
    System.out.println("싱글톤 객체 로직 호출");
  }

}

package hello.core.singleton;

public class SingletonService {

  // 1. static 영역에 객체를 딱 1개만 생성해둔다.
  private static final SingletonService instance = new SingletonService();

  // 2. public으로 열어서 객체 인스턴스가 필요한 이 static 메서드를 통해서만 조회하도록 한다.
  public static SingletonService getInstance() {
    return instance;
  }

  // 3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
  private SingletonService() {

  }

  public void logic() {
    System.out.println("싱글톤 객체 로직 호출");
  }

}
 */
