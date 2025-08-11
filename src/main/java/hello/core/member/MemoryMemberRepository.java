package hello.core.member;

import java.util.HashMap;
import java.util.Map;

// 메모리 회원 저장소 구현체
public class MemoryMemberRepository implements MemberRepository{

  private static Map<Long, Member> store = new HashMap<>(); // 데이터베이스라 가정.

  /*
  HashMap은 동시성 이슈가 발생할 수 있다.
  이런 경우 ConcurrentHashMap을 사용하자.
   */

  @Override
  public void save(Member member) { // 데이터베이스에 삽입
    store.put(member.getId(), member);
  }

  @Override
  public Member findById(Long memberId) { // 데이터베이스에서 조회
    return store.get(memberId);
  }
}

/*
package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {

  private static Map<Long, Member> store = new HashMap<>();

  @Override
  public void save(Member member) {
    store.put(member.getId(), member);
  }

  @Override
  public Member findById(Long memberId) {
    return store.get(memberId);
  }

}

package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {

  private static Map<Long, Member> store = new HashMap<>();

  @Override
  public void save(Member member) { // 회원 저장
    store.put(member.getId(), member); // 등록
  }

  @Override
  public Member findById(Long memberId) { // 데이터베이스에서 조회.
    return store.get(memberId);
  }

}
 */
