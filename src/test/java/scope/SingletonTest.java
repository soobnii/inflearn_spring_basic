package scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonTest {
	
	@Test
	void singletonBeanFind() {
		// AnnotationConfigApplicationContext 파라미터로 넣은 클래스는 자동으로 컴포넌트 스캔이 됨
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
		SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
		SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
		Assertions.assertThat(singletonBean1).isSameAs(singletonBean2);
		ac.close();
	}
	
	@Scope("singleton") // 디폴트값이라 생략 가능
	static class SingletonBean {
		@PostConstruct
		public void init() {
			System.out.println("SingletonBean.init");
		}
		
		@PreDestroy
		public void destroy() {
			System.out.println("SingletonBean.destroy");
		}
	}
}
