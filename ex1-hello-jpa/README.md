# JPA
 * 생산성
   * 저장 : jpa.persist(member)
   * 조회 : Member member = jpa.find(memberId)
   * 수정 : member.setName("변경할 이름")
   * 삭제 : jpa.remove(member)
 * 지연로딩과 즉시로딩
   * 지연 로딩: 객체가 실제 사용될 때 로딩 
   * 즉시 로딩: JOIN SQL로 한번에 연관된 객체까지 미리 조회
 * JPA 사용시 주의점
   * 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
   * 엔티티 매니저는 쓰레드간에 공유 X
   * JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
 * JPQL(객체지향 SQL)
   * JPA를 사용하면 엔티티 객체를 중심으로 개발
   * 문제는 검색 쿼리
   * 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
   * 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
   * 애플리케이션이 필요한 데이터만 DB 검색하려면 결국 검색 조건이 포함된 SQL 필요
   * JPQL은 엔티티 객체를 대상으로 쿼리
   * SQL은 데이터베이스 테이블을 대상으로 쿼리
 * 영속성 컨텍스트 이점
   * 1차캐시
   * 영속 엔티티의 동일성 보장
   * 엔티티 등록 트랜잭션을 지원하는 쓰기 지연
   * 엔티티 수정 변경 감지
   * 지연로딩
 * 엔티티 매핑
   * 객체와 테이블 매핑 
     * @Entity
       * @Entity가 붙은 클래스는 JPA가 관리
       * JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
       * 기본 생성자 필수
       * final 클래스, enum,interface,inner클래스 사용 X
       * 저장할 필드에 final 사용 X
     * @Table
       * DB의 테이블 이름과 매칭 시켜줄 수 있다
   * 필드와 컬럼 매핑  
     * @Column
       * DDL 생성기능 (unique, length 설정가능)
   * 기본 키 매핑: @Id
   * 연관관계 매핑: @ManyToOne, @JoinColumn
 * 데이타베이스 스키마 자동 생성
   * hibernate.hbm2ddl.auto 옵션
     * create 기존 테이블 삭제 후 다시 생성 (drop-create)
     * create-drop create와 같으나 종료시점에 drop
     * update 기존 테이블에 추가 (추가만 가능)
     * validate 엔티티와 테이블이 정상 매핑이 되었는지 확인 가능
   * 자동 생성시 주의점
     * 운영 장비에는 절대 create,create-drop,update사용 X