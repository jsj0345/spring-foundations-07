package hello.core.singleton;

// price는 변경하기 쉬움
public class StatefulService { // ctrl+shift+t를 누르면 테스트 생성 가능.

  private int price; // 상태를 유지하는 필드

  public void order(String name, int price) {
    System.out.println("name = " + name + " , price = " +price);
    this.price = price; // 이 부분이 문제.
  }

  public int getPrice() {
    return price;
  }

}

/*
// 상태를 유지할 경우 발생하는 문제점 예시.

package hello.core.singleton;

public class StatefulService {

  private int price; // 상태를 유지하는 필드.

  public void order(String name, int price) {
    System.out.println("name = " + name + " , price = " + price);
    this.price = price; // 이 부분이 문제.
  }

  public int getPrice() {
    return price;
  }

}

package hello.core.singleton;

public class StatefulService {

  private int price;

  public void order(String name, int price) {
    System.out.println("name = " + name + " price = " + price);
    this.price = price; // 이 부분이 문제다.
  }

  public int getPrice() {
    return price;
  }

}
 */