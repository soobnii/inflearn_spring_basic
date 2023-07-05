package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig_v1 { // 순수 JAVA 코드만 사용한 버전
	
	/*
	* 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
		-> MemberServiceImpl, MemoryMemberRepository, OrderServiceImpl, FixDiscountPolicy
	* 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해 주입(연결)해준다.
		-> MemoryMemberRepository 객체를 생성하고 그 참조값을 MemberServiceImpl 를 생성하면서 생성자로 전달한다.
		public MemberServiceImpl(MemberRepository memberRepository) {
			this.memberRepository = memberRepository;
		}
	* */
	
	public MemberService memberService() {
//		return new MemberServiceImpl(new MemoryMemberRepository());
		return new MemberServiceImpl(memberRepsoitory());
	}
	
	private MemberRepository memberRepsoitory() { // 구성 정보에서 역할과 구현을 명확하게 분리/파하기 위함
		return new MemoryMemberRepository();
	}
	
	public OrderService orderService() {
		return new OrderServiceImpl(memberRepsoitory(), discountPolicy());
	}
	
	private DiscountPolicy discountPolicy() {
		return new FixDiscountPolicy();
	}
}
