package hello.core;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
/*
원래 자바에선 Getter, Setter를 다 만들어줘야 했다.
그런데 Lombok은 @Getter, @Setter를 통해 알아서 만들어준다.
 */
public class HelloLombok {

  private String name;
  private int age;

  public static void main(String[] args) {
    HelloLombok helloLombok = new HelloLombok();
    helloLombok.setName("asdfas");

    String name = helloLombok.getName();
    System.out.println("name = " + name);

    System.out.println("helloLombok = " + helloLombok);
  }
}

// @Getter
// @Setter
// @ToString

/*
public class HelloLombok {

  private String name;
  private int age;

  public static void main(String[] args) {
    HelloLombok helloLombok = new HelloLombok();
    helloLombok.setName("asdfas");

    String name = helloLombok.getName();
    System.out.println("name = " + name);

    System.out.println("helloLombok = " + helloLombok);
  }


 }
 */