## 데이터 중심 설계의 문제점
 * 현재 방식은 객체 설계를 테이블 설계에 맞춘 방식
 * 테이블의 외래키를 객체에 그대로 가져옴
 * 객체 그래프 탐색이 불가능
 * 참조가 없으므로 UML도 잘못됨

## 연관관계 매핑
 * 단방향 연관관계
   * @ManyToOne, @JoinColumn, @OneToMany, mappedBy
 * 테이블은 양방향 연관관계, 객체는 단방향 2개로 양방향 연관관계
 * 연관관계의 주인(Owner)
   * 양방향 매핑 규칙
   * 객체의 두 관계중 하나를 연관관계의 주인으로 지정
   * 연관관계의 주인만이 외래키를 관리(등록,수정)
   * 주인이 아닌쪽은 mappedBy 사용
   * 주인이 아닌쪽은 읽기만 가능
   * 외래 키가 있는 곳을 주인으로 정해라
 * 양방향 매핑시 가장 많이 하는 실수
   * 연관관계의 주인에 값을 입력해야하는데 하지 않는 실수
   * JPA에서 mappedBy는 update나 등록시 신경 쓰지 않음
 * 양방향 연관관계 주의
   * 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다
   * 연관관계 편의 메소드를 생성하자(주인에서 연관관계에 있는 데이터까지 넣어주기)
   * 양방향 매핑시 무한 루프를 조심하자 
 * 양방향 매핑 정리
   * 단방향 매핑만으로도 이미 연관관계 매핑은 완료
   * 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
   * JPQL에서 역방향으로 탐색할 일이 많음
   * 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨(테이블에 영향을 주지 않음)
 * 연관관계 매핑시 고려사항 3가지
   * 다중성
     * 다대일 : @ManyToOne
       * Member와 Team N:1 일 때 N에 주인관계를 가져야함
     * 일대다 : @OneToMany
       * 1:N에서 1에 주인관계를 가지는 방 법은 실무에서는 잘 사용하지 않음
       * 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자
     * 일대일 : @OneToOne
       * 주 테이블이나 대상테이블 중에 외래 키 선택 가능
       * 다대일과 비슷하다 주인에 외래키 반대편에는 mappedBy적용 
       * 주테이블 외래키 설정시 : 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능 그러나 값이 없으면 null 허용
       * 대상테이블 외래키 설정시 : 일대일에서 일대다 관계로 변경할 때 테이블 유지 가능 그러나 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨
     * 다대다 : @ManyToMany (*실무에서 쓰면 안된다)
       * 연결테이블을 엔티티로 승격해서 다대일 일대다 사용해서 적용하는것을 추천
   * 단방향,양방향
     * 테이블
       * 외래 키 하나라 양쪽 조인 가능
       * 사실 방향이라는 개념이 없음
     * 객체
       * 참조용 필드가 있는 쪽으로만 참조 가능
       * 한쪽만 참조하면 단방향
       * 양쪽이 서로 참조하면 양방향
   * 연관관계의 주인
     * 주인 : 외래키를 관리하는 참조
     * 주인의 반대편 : 외래 키에 영향을 주지 않음, 단순 조회만 가능
   * 고급 매핑
     * 상속관계 매핑
       * 관계형 데이터베이스는 상속 관계X
       * 슈퍼타입 서브타입 관계라는 모델링 기법이 객체 상속과 유사
       * 객체의 상속 구조와 DB의 슈퍼타입 서브타입 관계를 매핑
       * @Inheritance
       * @DiscriminatorColumn(name="DTYPE")
       * @DiscriminatorValue("값지정가능")
     * 조인전략(@Inheritance(strategy = InheritanceType.JOINED))
       * 장점
         * 테이블 정규화
         * 외래키 참조 무결성 제약조건 활용가능
         * 저장공간 효율화
       * 단점
         * 조회시 조인을 많이 사용, 성능 저하
         * 조회쿼리 복잡
         * 데이터 저장시 INSERT SQL 2번 호출
     * 단일테이블 전략(@Inheritance(strategy = InheritanceType.SINGLE_TABLE))
       * 장점
         * 조인 필요 없으므로 일반적으로 조회 성능이 빠름
         * 조회쿼리 단순
       * 단점
         * 자식엔티티가 매핑한 컬럼은 모두 null 허용
         * 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다
         * 상황에 따라서 조회 성능이 오히려 느려질 수 있다
     * 구현 클래스마다 테이블 전략(@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS))
       * 장점
         * 서브 타입을 명확하게 구분해서 처리할 때 효과적
         * Not null 사용가능
       * 단점
         * 여러 자식 테이블을 함께 조회할 때 성능이 느림(UNION SQL)
         * 자식테이블 통합 쿼리 어려움
     * @MappedSuperClass
       * 공통적으로 사용할 필드를 한꺼번에 적용가능 (ex: BaseEntity)
       * 엔티티가 아님 (테이블 생성 안된다)
       * 상속관계 매핑 아님
       * 추상클래스 권장
## 프록시
 * 프록시 특징
   * 실제 클래스를 상속 받아서 만들어짐
   * 실제 클래스와 겉 모양이 같다.
   * 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 됨(이론상)
   * 프록시 객체는 처음 사용할 때 한 번만 초기화
   * 프록시 객체를 초기화 할때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님, 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
   * 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함(== 비교 실패, 대신 instance of 사용)
   * 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
   * 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제발생 (하이버네이트는 org.hibernate.LazyInitializationException 예외발생)
 * 프록시 확인
   * 프록시 인스턴스의 초기화 여부 확인
     * PersistenceUnitUtil.isLoaded(Object entity)
   * 프록시 클래스 확인 방법
     * entity.getClass()
   * 프록시 강제 초기화
     * org.hibernate.Hibernate.initialize(entity);
     * 참고 JPA 표준에는 강제 초기화 없음
 * 지연로딩
   * (fetch = FetchType.LAZY)로 설정시 실제 조회할 때 DB에서 가져와서 초기화함
   * 예) 멤버안에 팀이 있을 경우 팀에 lazy걸면 멤버조회시 팀은 프록시 객체로 가져오고 실제 팀을 조회할 때 초기화한다
 * 즉시로딩
   * (fetch = FetchType.EAGER)
   * 예) 멤버안에 팀이 있을 경우 멤버조회시 항상 팀도 조회
 * 프록시와 즉시로딩 주의
   * 가급적 지연 로딩만 사용(특히 실무에서)
   * 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
   * 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다
   * @ManyToOne, @OneToOne은 기본이 즉시 로딩 (=> LAZY로 설정해주는게 좋음)
   * @OneToMany, @ManyToMany는 기본이 지연 로딩
 * 영속성 전이 : CASCADE
   * 하나의 부모객체가 여러 자식 객체를 관리할 때 부모만 persist해줘도 자식까지 insert쿼리가 나가게 하는 방식
 * 고아 객체
   * 고아 객체 제거: 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
   * orphanRemoval = true
   * 참조하는 곳이 하나일 때 사용해야함!
   * 특정 엔티티가 개인 소유할 때 사용
## JPA 데이터 타입 분류
 * 엔티티 타입
   * @Entity로 정의하는 객체
   * 데이터가 변해도 식별자로 지속해서 추적 가능
   * 예) 회원 엔티티의 키나 나이 값을 변경해도 식별자로 인식 가능
 * 값 타입
   * int,Integer,String 처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
   * 식별자가 없고 값만 있으므로 변경시 추적 불가
   * 예) 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체
 * 값 타입 분류
   * 기본값 타입
     * 자바 기본 타입(int,double)
     * 래퍼 클래스(Integer,Long)
     * String
     * 예) String name, int age
     * 생명주기를 엔티티의 의존
       * 예) 회원을 삭제하면 이름, 나이 필드도 함께 삭제
     * 값 타입은 공유하면 안된다
       * 예)회원 이름 변경시 다른 회원의 이름도 변경되면 안된다.
     * 참고: 자바의 기본 타입은 절대 공유하지 않는다
     * 기본 타입은 항상 값을 복사함
   * 임베디트 타입(embedded type, 복합 값 타입)
     * 새로운 값 타입을 직접 정의할 수 있음
     * 주로 기본 값 타입을 모아서 만들어 복합 값 타입이라고도 함
     * int, String과 같은 값 타입
     * @Embeddable: 값 타입을 정의하는 곳에 표시
     * @Embedded: 값 타입을 사용하는 곳에 표시
     * 기본 생성자 필수 
     * 임베디드 타입은 엔티티의 값일 뿐이다
     * 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다
     * 객체와 테이블을 세밀하게 매핑하는 것이 가능하다
     * 임베디드 타입이 null이면 해당 타입 내 필드 값 다 null
 * 값 타입의 비교
   * 동일성(identity) 비교 :  인스턴스의 참조 값을 비교, == 사용
   * 동등성(equivalence) 비교 : 인스턴스의 값을 비교, equals()사용
 * 값 타입 컬렉션
   * 값 타입을 하나 이상 저장할 때 사용
   * @ElementCollection, @CollectionTable(name="테이블명",joinColumns = "컬럼_id") 사용
   * 데이터베이스는 컬렉션을 같은 테이블에 저장 할 수 없다
   * 컬렉션을 저장하기 위한 별도의 테이블이 필요함
   * 영속성 전에(Cascade) + 고아 객체 제거 기능을 필수로 가진다고 볼 수 있다
   * 지연로딩 전략 사용
 * 값 타입 컬렉션의 제약사항
   * 값 타입은 엔티티와 다르게 식별자 개념이 없다
   * 값을 변경하면 추적이 어렵다
   * 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다
   * 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야함: null 입력 X, 중복 저장 X
   * 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
   
## JPQL
 * 엔티티 객체를 대상으로 쿼리한다
 *  