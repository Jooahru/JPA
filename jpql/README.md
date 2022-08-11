## JPQL
 * JPQL 문법
   * select m from Member as m wher m.age > 18
   * 엔티티와 속성은 대소문자 구분O(Member,age)
   * JPQL 키워드는 대소문자 구분X (SELECT,FROM,where)
   * 엔티티 이름 사용, 테이블 이름이 아님(Member)
   * 별칭은 필수(m) (as는 생략 가능)
 * 집합과 정렬 
   * select COUNT(m), SUM(m.age),AVG(m.age),MAX(m.age),MIN(m.age) from Member m
   * GROUP BY,HAVING, ORDER BY
 * TypeQuery, Query
   * TypeQuery : 반환 타입이 명확할 때 사용
   * Query: 반환 타입이 명확하지 않을 때 사용
 * 결과 조회 API
   * query.getResultList(): 결과가 하나 이상일 때, 리스트 반환
     * 결과가 없으면 빈 리스트 반환
   * query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
     * 결과가 없으면 : javax.persistence.NoResultException
     * 둘 이상이면 : javax.persistence.NonUniqueResultException
 * 파라미터 바인딩 - 이름 기준, 위치 기준
   * SELECT m FROM Member m where m.username = :username
   * query.setParameter("username",usernameParam); (체인으로 구현 가능)
   * ==================================================
   * SELECT m FROM Member m wher m.username=?1
   * query.setParameter(1,usernameParam);
 * 프로젝션
   * SELECT절에 조회할 대상을 지정하는 것
   * 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라타입(숫자,문자등 기본데이터 타입)
   * SELECT m.team FROM Member m => 엔티티 프로젝션
   * SELECT m FROM Member m => 엔티티 프로젝션
   * SELECT m.address FROM Member m => 임베디드 타입 프로젝션
   * SELECT m.username, m.age FROM Member m => 스칼라 타입 프로젝션
   * DISTINCT로 중복 제거
 * 프로젝션 - 여러 값 조회
   * Query 타입으로 조회
   * Object[] 타입으로 조회
   * new 명령어로 조회
     * 단순 값을 DTO로 바로 조회
 * 페이징 API
   * JPA는 페이징을 다음 두 API로 추상화
     * setFirstResult(int startPosition): 조회 시작 위치(0부터 시작)
     * setMaxResults(int maxResult): 조회할 데이터 수