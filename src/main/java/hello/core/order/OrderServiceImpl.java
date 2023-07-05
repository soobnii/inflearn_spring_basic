package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	// private final MemberRepository memberRepository = new MemoryMemberRepository();
	// private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
	// private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
	
	/* 1) DIP 위반 - 의존관계가 인터페이스뿐만 아니라 구현까지 의존
		주문서비스 클라이언트 OrderServiceImpl 은 DiscountPolicy 인터페이스에 의존(추상화엥 의존)하면서 DIP 를 지킨것 같지만, NO
		구체(구현) 클래스(FixDiscountPolicy, RateDiscountPolicy)에도 함께 의존하고 있다. -> 인터페이스에만 의존하도록 변경해야 함
		
		2) OCP 위반
		OCP 는 변경하지 않고 확장해야하지만, 할인정책 변경 시 클라이언트 코드에 영향을 준다. (변경해야 함)
	* */
	
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;
	
	/* 단, 누군가가 클라이언트 OrderServiceImpl에 DiscountPolicy 의 구현 객체를 대신 생성하고 주입해야함
	*
	* 관심사의 분리
		공연 기획자 - 배우의 역할과 책임이 나누어져야 하는데, 클라이언트(배우)가 구현체를(상대배우) 선택하는 일을 하는 것과 동일함
		-> 어플리케이션의 전체 동작 방식을 구성(config) 하기 위해, 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스를 만들어야 함
			(공연 기획자 역할. AppConfig)
			
		SRP 단일 책임의 원칙 : 한 클래스는 하나의 책임만 가져야 한다.
			클라이언트는 객체는 실행하는 책임만 가지고, 구현 객체를 생성하고 연결하는 책임은 별도 클래스가 담당함함
	*
	* IoC : 제어의 역전
		기존엔 클라이언트 구현 객체가 스스로 필요한 서버 구현 객체를 생성하고, 연결하고, 실행했다.
		하지만 현재는 프로그램에 대한 제어 흐름에 대한 권한은 모두 AppConfig 가 가지고 있다.
		여기서 AppConfig 는 OrderServiceImpl 도 생성하고, 혹은 OrderService 의 다른 구현 객체를 생성하고 실행할 수도 있다.
		OrderServiceImpl 는 이 사실을 모른채 묵묵히 자신의 로직을 실행할 뿐이다.
		즉, 프로그램의 제어 흐름을 직접 제어하는것이 아니라 외부에서 관리하는 것을 IoC라 한다.
	**/
	
	@Autowired
	public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}
	
	/* 어떤 구현객체가 들어올지는 알 수 없다. 오직 외부(AppConfig)에 의해서 결정됨
		의존관계에 대한 고민은 외부에 마티곡, 실행에만 집중하는 것
		즉, 의존관계를 외부에서 주입해주는 것 -> 의존관계 주입 or 의존성 주입 (DI)
	*/
	
	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}
}
