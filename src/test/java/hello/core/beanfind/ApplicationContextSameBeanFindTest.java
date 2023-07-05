package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextSameBeanFindTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);
	
	@Test
	@DisplayName("타입으로 조회 시 같은 타입이 둘 이상있으면, 중복 오류가 발생한다.")
	void findBeanByTypeDuplicate() {
//		MemberRepository bean = ac.getBean(MemberRepository.class);
		Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () ->
			ac.getBean(MemberRepository.class)
		);
	}
	
	@Test
	@DisplayName("타입으로 조회 시 같은 타입이 둘 이상있으면, 빈 이름을 지정하면 된다.")
	void findBeanByName() {
		MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
		assertThat(memberRepository).isInstanceOf(MemberRepository.class);
	}
	
	@Test
	@DisplayName("특정 타입을 모두 조회하기")
	void findAllBeanByType() {
		Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
		for (String key : beansOfType.keySet()) {
			System.out.println("key = " + key + " value = " + beansOfType.get(key));
		}
		System.out.println("beansofType = " + beansOfType);
		assertThat(beansOfType.size()).isEqualTo(2);
	}
	
	@Configuration
	static class SameBeanConfig { // 테스트를 위함
		/* static이 아닌 이너 클래스의 인스턴스는 바깥 클래스의 인스턴스와 암묵적으로 연결된다.
			왜냐하면 static이 아닌 이너 클래스는 바깥 인스턴스 없이는 생성할 수 없기 때문이다.
			두 클래스의 관계는 이너 클래스의 인스턴스 안에 만들어지며, 메모리를 차지한다. 생성도 느리다.
			
			이너 클래스에서 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여서 정적 멤버 클래스로 만들자.
			static을 생략하면 바깥 인스턴스로의 숨은 외부 참조를 갖게 된다.
			앞서도 얘기했듯 이 참조를 저장하려면 시간과 공간이 소비된다.
			더 심각한 문제는 가비지 컬렉션이 바깥 클래스의 인스턴스를 수거하지 못하는 메모리 누수가 생길 수 있다는 점이다(아이템 7).
			(바깥 클래스 인스턴스의 참조를 이너 클래스가 갖고 있으므로, 바깥 클래스 인스턴스가 쓰레기 수거 대상에서 빠지게 된다.)
			참조가 눈에 보이지 않으니 문제의 원인을 찾기 어려워 때때로 심각한 상황을 초래하기도 한다.
		*/
		
		@Bean
		public MemberRepository memberRepository1() {
			return new MemoryMemberRepository();
		}
		
		@Bean
		public MemberRepository memberRepository2() {
			return new MemoryMemberRepository();
		}
	}
}
