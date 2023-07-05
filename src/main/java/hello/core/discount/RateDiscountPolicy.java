package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
// @Qualifier("mainDiscountPolicy") // 이렇게 쓰면 오타나도 컴파일시점엔 알 수 없음
public class RateDiscountPolicy implements DiscountPolicy {
	
	private int discountPercent = 10;
	
	@Override
	public int discount(Member member, int price) {
		if(member.getGrade() == Grade.VIP) {
			return price * discountPercent / 100;
		} else {
			return 0;
		}
	}
}
