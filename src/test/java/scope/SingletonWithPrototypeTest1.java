package scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

	@Test
	void singletonClientUsePrototype() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
		
		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		int count1 = clientBean1.logic();
		assertThat(count1).isEqualTo(1);
		
		ClientBean clientBean2 = ac.getBean(ClientBean.class);
		int count2 = clientBean2.logic();
		assertThat(count2).isEqualTo(1);
	}
	
	static class ClientBean { // 디폴트 싱글톤
	
		// private final PrototypeBean prototypeBean;
		
		/*
		@Autowired
		public ClientBean(PrototypeBean prototypeBean) {
			this.prototypeBean = prototypeBean;
		}
		*/
		
		@Autowired
		private Provider<PrototypeBean> prototypeBeanProvider;
		
		public int logic() {
			// getObject 호출할때 스프링 컨테이너에서 프로토타입 빈을 찾아 반환해줌
			// 어플리케이션컨텍스트한테 직접 찾는것이 아님
			PrototypeBean prototypeBean = prototypeBeanProvider.get();
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}
	
	@Scope("prototype")
	static class PrototypeBean {
	
		private int count = 0;
		
		public void addCount() {
			count++;
		}
	
		public int getCount() {
			return count;
		}
		
		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init " + this);
		}
		
		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean.destroy");
		}
	}
}
