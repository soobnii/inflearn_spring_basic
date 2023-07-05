package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {
	
	@Test
	void findAllBean() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
		// AutoAppConfig : @ComponentScan
		// FixDiscountPolicy, RateDiscountPolicy 모두 @Component 존재하여 빈 등록됨
		// DiscountService 도 빈으로 등록
		
		/*
		* 스프링 컨테이너는 생성자에 클래스 정보를 받는다. 여기에 클래스 정보를 넘기면 해당 클래스가 스프링 빈으로 자동 등록된다.
		* new AnnotationConfigApplicationContext() 를 통해 스프링 컨테이너를 생성한다.
		* AutoAppConfig.class , DiscountService.class 를 파라미터로 넘기면서 해당 클래스를 자동으로 스프링 빈으로 등록한다.
		* 정리하면 스프링 컨테이너를 생성하면서, 해당 컨테이너에 동시에 AutoAppConfig , DiscountService 를 스프링 빈으로 자동 등록한다.
		* */
		
		DiscountService discountService = ac.getBean(DiscountService.class);
		Member member = new Member(1L, "userA", Grade.VIP);
		
		int fixDiscountPrice = discountService.discount(member, 20000, "fixDiscountPolicy");
		Assertions.assertThat(fixDiscountPrice).isEqualTo(1000);
		
		int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
		Assertions.assertThat(rateDiscountPrice).isEqualTo(2000);
	}

	static class DiscountService {
		private final Map<String, DiscountPolicy> policyMap;
		private final List<DiscountPolicy> policyList;
		
		@Autowired // 생성자 한개이므로 생략 가능
		public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
			this.policyMap = policyMap;
			this.policyList = policyList;
			System.out.println("policyMap = " + policyMap);
			System.out.println("policyList = " + policyList);
			// policyMap = {fixDiscountPolicy=hello.core.discount.FixDiscountPolicy@3f3c966c, rateDiscountPolicy=hello.core.discount.RateDiscountPolicy@11ee02f8},
			/*
			* DiscountService는 Map으로 모든 DiscountPolicy 를 주입받는다.
			* 이때 fixDiscountPolicy, rateDiscountPolicy 가 주입된다.
			* */
		}
		
		public int discount(Member member, int price, String discountCode) {
			DiscountPolicy discountPolicy = policyMap.get(discountCode);
			return discountPolicy.discount(member, price);
			/* discount () 메서드는 discountCode로 "fixDiscountPolicy"가 넘어오면
			* map에서 fixDiscountPolicy 스프링 빈을 찾아서 실행한다.
			* */
		}
	}
}
