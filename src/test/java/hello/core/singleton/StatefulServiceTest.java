package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {
	
	@Test
	void statefulServiceSingleton() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);
		
		// 실제 스레드는 사용안하고 예시를 위해 간단히..
		// Thread A : A 사용자가 10000원 주문
		int userAprice = statefulService1.order("userA", 10000);
		
		// Thread B : B 사용자가 20000원 주문
		int userBprice =  statefulService2.order("userB", 20000);

		// Thread A : A 사용자가 주문 금액 조회
//		int price = statefulService1.getPrice();
//		System.out.println("price = " + price); // 20000원이 나온다.
		
		assertThat(userAprice).isEqualTo(10000);
	}
	
	static class TestConfig {
		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
	}
}