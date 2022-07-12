# JPA
 * 생산성
   * 저장 : jpa.persist(member)
   * 조회 : Member member = jpa.find(memberId)
   * 수정 : member.setName("변경할 이름")
   * 삭제 : jpa.remove(member)
 * 지연로딩과 즉시로딩
   * 지연 로딩: 객체가 실제 사용될 때 로딩 
   * 즉시 로딩: JOIN SQL로 한번에 연관된 객체까지 미리 조회