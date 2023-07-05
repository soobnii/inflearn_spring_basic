package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
	
	@Test
	public void lifeCycleTest() {
		// ApplicationContext > ConfigurableApplicationContext > AnnotationConfigApplicationContext
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
		NetworkClient client = ac.getBean(NetworkClient.class);
		ac.close();
		// 생성자 호출 url = null
		// connect : null
		// call = null message = 초기화 연결 메시지
	}
	
	@Configuration
	static class LifeCycleConfig {
		
		@Bean
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("http://hello-spring.dev");
			return networkClient;
		}
	}
}
