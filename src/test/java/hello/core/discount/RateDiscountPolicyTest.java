package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyTest {
	
	RateDiscountPolicy discountPolicy = new RateDiscountPolicy();
	
	@Test
	@DisplayName("VIP는 10% 할인")
	void discount_vip_o() {
		// given
		Member member = new Member(1L, "memberVIP", Grade.VIP);
		
		// when
		int discount = discountPolicy.discount(member, 10000);
		
		// then
		assertThat(discount).isEqualTo(1000);
	}
	
	@Test
	@DisplayName("VIP가 아니면 10% 할인 적용 x")
	void discount_vip_x() {
		// given
		Member member = new Member(1L, "memberBASIC", Grade.BASIC);
		
		// when
		int discount = discountPolicy.discount(member, 10000);
		
		// then
		assertThat(discount).isEqualTo(0);
	}
}