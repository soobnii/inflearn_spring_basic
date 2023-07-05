package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

//	MemberService memberService = new MemberServiceImpl();
//	OrderService orderService = new OrderServiceImpl();
	
	MemberService memberService;
	OrderService orderService;
	
	@BeforeEach
	public void beforeEach() {
		AppConfig appConfig = new AppConfig();
		memberService = appConfig.memberService();
		orderService = appConfig.orderService();
	}
	
	@Test
	void createOrder() {
		Long memberId = 1L; // primitive 타입인 long 은 null 대입이 불가하여 래퍼타입 사용하였음 (추후에 null 대임할수도있어서)
		Member member = new Member(memberId, "memberA", Grade.VIP);
		memberService.join(member);
		
		Order order = orderService.createOrder(memberId, "itdmA", 10000);
		Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
	}
	
}
