package hello.core.member;

public class Member {

  private Long id;
  private String name;
  private Grade grade;

  public Member(Long id, String name, Grade grade) {
    this.id = id;
    this.name = name;
    this.grade = grade;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Grade getGrade() {
    return grade;
  }

  public void setGrade(Grade grade) {
    this.grade = grade;
  }

  /*
  Settings에 Keymap으로 가면 어떤 단축키를 누르면 어떤 기능이 나오는지 알 수 있음.
   */
}

/*
회원 엔티티

package hello.core.member;

public class Member {

  private Long id;
  private String name;
  private Grade grade;

  public Member(Long id, String name, Grade grade) {
    this.id = id;
    this.name = name;
    this.grade = grade;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Grade getGrade() {
    return grade;
  }

  public void setGrade(Grade grade) {
    this.grade = grade;
  }

}

package hello.core.member;

public class Member {

  private Long id;
  private String name;
  private Grade grade;

  public Member(Long id, String name, Grade grade) {
    this.id = id;
    this.name = name;
    this.grade = grade;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Grade getGrade() {
    return grade;
  }

  public void setGrade(Grade grade) {
    this.grade = grade;
  }


}



 */
